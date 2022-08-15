package com.zqf.pomodoroschedule

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import com.zqf.pomodoroschedule.model.BasicSettings
import javax.swing.JComponent

class PomodoroBasicSettingPresenter constructor(private val basicSettings: BasicSettings = BasicSettings.instance): SearchableConfigurable {
    private lateinit var uiModel: BasicSettings
    private var basicSettingsWindowFrom: PomodoroBasicSettingsWindow? = null

    override fun createComponent(): JComponent? {
        basicSettingsWindowFrom = PomodoroBasicSettingsWindow()
        uiModel = BasicSettings()
        return basicSettingsWindowFrom?.content
    }

    override fun isModified() = uiModel != basicSettings

    @Throws(ConfigurationException::class)
    override fun apply() {
        basicSettings?.loadState(uiModel)
    }

    override fun getDisplayName() = UIBundle.message("settings.title")

    override fun getId() = "pomodoro schedule"
}