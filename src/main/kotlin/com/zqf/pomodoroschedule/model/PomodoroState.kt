package com.zqf.pomodoroschedule.model

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.OptionTag
import com.zqf.pomodoroschedule.model.PomodoroState.Mode.*
import com.zqf.pomodoroschedule.model.time.Duration
import com.zqf.pomodoroschedule.model.time.Time

/*
* leftPomodoros: 剩余pomodoro数量
* currentMode: 当前pomodoro状态
* leftPomodorosTimeSpan: 当前pomodoro剩余时间
*/
@State(name = "PomodoroStates", storages = [Storage("pomodoro.state.xml")] )
data class PomodoroState(
    var leftPomodoros: Int = 1,
    @Transient var currentMode: Mode = Stop,
    var leftPomodorosTimeSpan: Long = 0L,
    var progress: Duration = Duration.zero,
    var pomodorosTillLongBreak: Int = BasicSettings.defaultLongBreakFrequency,
    var lastMode: Mode = Stop,
    var startTime: Time = Time.zero,
    var lastUpdateTime: Time = Time.zero,
): PersistentStateComponent<PomodoroState> {

    enum class Mode {
        /** Pomodoro in progress. */
        Run,
        /** Pomodoro during break. Can only happen after completing a pomodoro. */
        Break,
        /** Pomodoro timer was not started or was stopped during pomodoro or break. */
        Stop
    }

    override fun getState() = this

    override fun loadState(state: PomodoroState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}