package com.zqf.pomodoroschedule

import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep as BaseListPopupStep1

class PopupListPlugin(title: String, fruits: List<String>): BaseListPopupStep1<String>(title, fruits) {
    override fun isSpeedSearchEnabled(): Boolean {
        return true
    }

    override fun onChosen(selectedValue: String?, finalChoice: Boolean): PopupStep<*>? {
        print("")
        return PopupStep.FINAL_CHOICE
    }
}