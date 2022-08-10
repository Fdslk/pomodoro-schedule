package com.zqf.pomodoroschedule.group

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.zqf.pomodoroschedule.MainAction

class PomodoroSettingsAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        MainAction().showDialog()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
    }
}