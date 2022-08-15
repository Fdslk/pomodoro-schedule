package com.zqf.pomodoroschedule

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory

class MainAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        showDialog()
    }

    private fun showPopupList(e: AnActionEvent) {
        val popupListPlugin = PopupListPlugin("different fruits", mutableListOf("apple", "pear", "peach", "pineapple"))
        if (e.project != null) {
            JBPopupFactory.getInstance().createListPopup(popupListPlugin, 5).showCenteredInCurrentWindow(e.project!!)
        }
    }

    fun showDialog() {
        val pluginWrapper = DataDialogWrapper()
        if (DataDialogWrapper().showAndGet()) {
            pluginWrapper.close(0)
        }
    }
}