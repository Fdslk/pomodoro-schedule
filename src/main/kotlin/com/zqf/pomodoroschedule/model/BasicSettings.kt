package com.zqf.pomodoroschedule.model

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "PomodoroBasicSetting", storages = [Storage("pomodoro.basic.settings.xml")])
data class BasicSettings(
    var pomodoroDuration: Int = defaultPomodoroDuration,
    var breakDuration: Int = defaultBreakDuration,
    var longBreakDuration: Int = defaultLongBreakDuration,
    var longBreakFrequency: Int = defaultLongBreakFrequency,
    var ringVolume: Int = 1,
    var isPopupEnabled: Boolean = true,
    var isShowTimeInToolbarWidget: Boolean = true,
    var startNewPomodoroAfterBreak: Boolean = false,
    var notificationMessages: String = "",
    var isOpenRing: Boolean = false
) : PersistentStateComponent<BasicSettings> {
    val timeoutToContinuePomodoro = defaultBreakDuration
    private val changeListeners = ArrayList<ChangeListener>()

    fun addChangeListener(changeListener: ChangeListener) {
        changeListeners.add(changeListener)
    }

    fun removeChangeListener(changeListener: ChangeListener) {
        changeListeners.remove(changeListener)
    }

    override fun getState() = this

    override fun loadState(state: BasicSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    interface ChangeListener {
        fun onChange(newSettings: BasicSettings)
    }

    companion object {
        const val defaultPomodoroDuration = 25
        const val defaultBreakDuration = 5
        const val defaultLongBreakDuration = 20
        const val defaultLongBreakFrequency = 4

        val instance: BasicSettings
            get() = service()
    }
}