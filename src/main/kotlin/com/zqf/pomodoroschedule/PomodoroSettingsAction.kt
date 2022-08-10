package com.zqf.pomodoroschedule

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class PomodoroSettingsAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        MainAction().showDialog()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
    }
}