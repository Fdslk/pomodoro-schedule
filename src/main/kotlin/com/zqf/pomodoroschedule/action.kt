package com.zqf.pomodoroschedule

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.zqf.pomodoroschedule.model.PomodoroState.Mode.*
import com.zqf.pomodoroschedule.model.time.Time

class StartOrStopPomodoro : AnAction(), DumbAware {
    override fun actionPerformed(event: AnActionEvent) {
        service<PomodoroService>().model.onUserSwitchToNextState(Time.now())
    }

    override fun update(event: AnActionEvent) {
        val mode = service<PomodoroService>().model.state.currentMode
        event.presentation.text = when (mode) {
            Run -> "Stop Pomodoro Timer"
            Break -> "Stop Pomodoro Timer"
            Stop  -> "Start Pomodoro Timer"
        }
    }
}

class ResetPomodorosCounter : AnAction("Reset Pomodoros Counter"), DumbAware {
    override fun actionPerformed(event: AnActionEvent) {
        service<PomodoroService>().model.resetPomodoros()
    }
}