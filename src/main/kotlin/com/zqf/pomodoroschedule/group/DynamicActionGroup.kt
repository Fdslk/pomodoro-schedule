package com.zqf.pomodoroschedule.group

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.zqf.pomodoroschedule.MainAction

class DynamicActionGroup: ActionGroup() {
    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return arrayOf(PomodoroSettingsAction(), MainAction())
    }
}