package com.zqf.pomodoroschedule.model

import com.zqf.pomodoroschedule.model.PomodoroState.Mode.*
import com.zqf.pomodoroschedule.model.time.Duration
import com.zqf.pomodoroschedule.model.time.Time

class PomodoroModel(originalSettings: BasicSettings, val state: PomodoroState) {
    private val listeners = HashMap<Any, Listener>()
    private var settings = originalSettings.copy()
    private var updatedSettings = settings

    init {
        originalSettings.addChangeListener(object: BasicSettings.ChangeListener {
            override fun onChange(newSettings: BasicSettings) {
                updatedSettings = newSettings
            }
        })
        state.progress = progressMax
        state.pomodorosTillLongBreak = settings.longBreakFrequency
    }

    fun onIdeStartup(time: Time) = state.apply {
        if (currentMode != Stop) {
            val shouldNotContinuePomodoro = Duration.between(lastUpdateTime, time) > Duration(minutes = settings.timeoutToContinuePomodoro)
            if (shouldNotContinuePomodoro) {
                currentMode = Stop
                lastMode = Stop
                startTime = Time.zero
                progress = Duration.zero
            } else {
                progress = progressSince(time)
            }
        }
    }

    fun onUserSwitchToNextState(time: Time) = state.apply {
        onTimer(time)
        settings = updatedSettings
        var wasManuallyStopped = false
        when (currentMode) {
            Run   -> {
                currentMode = Stop
                progress = progressMax
                wasManuallyStopped = true
                if (pomodorosTillLongBreak == 0) {
                    pomodorosTillLongBreak = settings.longBreakFrequency
                }
            }
            Break -> {
                currentMode = Stop
                progress = progressMax
                wasManuallyStopped = true
            }
            Stop  -> {
                currentMode = Run
                startTime = time
            }
        }
        onTimer(time, wasManuallyStopped)
    }


    fun onTimer(time: Time, wasManuallyStopped: Boolean = false) = state.apply {
        when (currentMode) {
            Run   -> {
                progress = progressSince(time)
                if (time >= startTime + progressMax) {
                    currentMode = Break
                    settings = updatedSettings
                    startTime = time
                    progress = progressSince(time)
//                    pomodorosAmount++
                    pomodorosTillLongBreak--
                }
            }
            Break -> {
                progress = progressSince(time)
                if (time >= startTime + progressMax) {
                    settings = updatedSettings
                    if (settings.startNewPomodoroAfterBreak) {
                        currentMode = Run
                        startTime = time
                    } else {
                        currentMode = Stop
                    }
                    progress = progressMax
                }
            }
            Stop  -> if (lastMode == Stop) {
                return@apply
            }
        }

        listeners.values.forEach { it.onStateChange(this, wasManuallyStopped) }

        lastMode = currentMode
        lastUpdateTime = time
    }

    fun addListener(key: Any, listener: Listener) {
        listeners[key] = listener
    }

    fun resetPomodoros() {
        state.leftPomodoros = 0
    }

    val leftPomodorosTimeSpan: Duration
        get() = progressMax - state.progress

    private fun progressSince(time: Time): Duration =
        Duration.between(state.startTime, time).capAt(progressMax)

    fun removeListener(key: Any) {
        listeners.remove(key)
    }

    private val progressMax: Duration
        get() = when (state.currentMode) {
            Run -> Duration(settings.pomodoroDuration)
            Break ->
                if (state.pomodorosTillLongBreak == 0) Duration(settings.longBreakDuration)
                else Duration(settings.breakDuration)
            else  -> Duration.zero
        }

    interface Listener {
        fun onStateChange(state: PomodoroState, wasManuallyStopped: Boolean)
    }
}