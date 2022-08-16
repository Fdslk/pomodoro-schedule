package com.zqf.pomodoroschedule

import com.intellij.openapi.components.service
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.roots.ui.whenTextModified
import com.zqf.pomodoroschedule.model.BasicSettings
import java.awt.event.ActionListener
import javax.swing.JComponent

class PomodoroBasicSettingPresenter constructor(private val basicSettings: BasicSettings = service()): SearchableConfigurable {
    private lateinit var uiModel: BasicSettings
    private var basicSettingsWindowFrom: PomodoroBasicSettingsWindow? = null
    private var updatingUI: Boolean = false

    override fun createComponent(): JComponent? {
        basicSettingsWindowFrom = PomodoroBasicSettingsWindow()
        uiModel = BasicSettings()

        setupUIBindings()

        return basicSettingsWindowFrom?.content
    }

    private fun setupUIBindings() {

        val actionListener = ActionListener {
            updateBasicSettingsModel()
            updateUI()
        }

        basicSettingsWindowFrom!!.apply {
            cBox2PomodoroLength.addActionListener(actionListener)
            cBox2BreakTimeSpan.addActionListener(actionListener)
            cBox2longBreakTimeSpan.addActionListener(actionListener)
            count2Pomodoros.addActionListener(actionListener)
            multiText2NotificationMessages.whenTextModified {
                if(uiModel.notificationMessages != basicSettingsWindowFrom?.multiText2NotificationMessages?.text){
                    uiModel.notificationMessages = basicSettingsWindowFrom?.multiText2NotificationMessages?.text.toString()
                }
            }
        }
    }

    private fun updateBasicSettingsModel() {
        if (uiResourcesDisposed()) return
        if (updatingUI) return

        if(uiModel.breakDuration != basicSettingsWindowFrom?.cBox2BreakTimeSpan?.model?.selectedItem.toString().toInt()){
            uiModel.breakDuration =  basicSettingsWindowFrom?.cBox2BreakTimeSpan?.model?.selectedItem.toString().toInt()
        }
        if(uiModel.pomodoroDuration != basicSettingsWindowFrom?.cBox2PomodoroLength?.model?.selectedItem.toString().toInt()){
            uiModel.pomodoroDuration = basicSettingsWindowFrom?.cBox2PomodoroLength?.model?.selectedItem.toString().toInt()
        }
        if(uiModel.longBreakDuration != basicSettingsWindowFrom?.cBox2longBreakTimeSpan?.model?.selectedItem.toString().toInt()){
            uiModel.longBreakDuration =  basicSettingsWindowFrom?.cBox2longBreakTimeSpan?.model?.selectedItem.toString().toInt()
        }
        if(uiModel.longBreakFrequency != basicSettingsWindowFrom?.count2Pomodoros?.model?.selectedItem.toString().toInt()){
            uiModel.longBreakFrequency =  basicSettingsWindowFrom?.count2Pomodoros?.model?.selectedItem.toString().toInt()
        }
    }

    private fun updateUI() {
        if (uiResourcesDisposed()) return
        if (updatingUI) return
        updatingUI = true

        basicSettingsWindowFrom!!.apply {
            cBox2PomodoroLength.model.selectedItem = uiModel.pomodoroDuration
            cBox2BreakTimeSpan.model.selectedItem = uiModel.breakDuration
            cBox2longBreakTimeSpan.model.selectedItem = uiModel.longBreakDuration
            count2Pomodoros.model.selectedItem = uiModel.longBreakFrequency
            multiText2NotificationMessages.text = uiModel.notificationMessages
        }

        updatingUI = false
    }

    override fun isModified() = uiModel != basicSettings

    @Throws(ConfigurationException::class)
    override fun apply() {
        basicSettings?.loadState(uiModel)
    }

    override fun getDisplayName() = UIBundle.message("settings.title")

    override fun getId() = "pomodoro schedule"

    override fun reset() {
        uiModel.loadState(basicSettings)
        updateUI()
    }

    private fun uiResourcesDisposed() = basicSettingsWindowFrom == null
}