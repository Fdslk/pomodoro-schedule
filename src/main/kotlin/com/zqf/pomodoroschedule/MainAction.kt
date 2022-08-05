package com.zqf.pomodoroschedule

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class MainAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if(DataDialogWrapper().showAndGet()){

        }
        val userName =
            Messages.showInputDialog(e.project, "Enter your name", "Your Name", Messages.getQuestionIcon())
        Messages.showMessageDialog(e.project, "Your name is $userName", "Your Name", Messages.getQuestionIcon())
        TODO("Not yet implemented")
    }
}