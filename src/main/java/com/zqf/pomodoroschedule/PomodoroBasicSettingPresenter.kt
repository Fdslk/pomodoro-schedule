package com.zqf.pomodoroschedule

import com.intellij.openapi.components.service
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.roots.ui.whenTextModified
import com.zqf.pomodoroschedule.model.BasicSettings
import com.zqf.pomodoroschedule.model.TimeUtils
import java.awt.event.ActionListener
import java.util.*
import javax.swing.JComponent
import kotlin.concurrent.fixedRateTimer

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
            startPomodorobutton.addActionListener(actionListener)
            cBox2ringNotification.addActionListener(actionListener)
            cBox2AutoStartForNext.addActionListener(actionListener)
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
        if(uiModel.isOpenRing != basicSettingsWindowFrom?.cBox2ringNotification?.model?.isSelected) {
            uiModel.isOpenRing = basicSettingsWindowFrom?.cBox2ringNotification?.model?.isSelected!!
        }
        if(uiModel.startNewPomodoroAfterBreak != basicSettingsWindowFrom?.cBox2AutoStartForNext?.model?.isSelected) {
            uiModel.startNewPomodoroAfterBreak = basicSettingsWindowFrom?.cBox2AutoStartForNext?.model?.isSelected!!
        }
    }

    private fun updateUI() {
        var index = uiModel.pomodoroDuration
        if (uiResourcesDisposed()) return
        if (updatingUI) return
        updatingUI = true

        basicSettingsWindowFrom!!.apply {
            cBox2PomodoroLength.model.selectedItem = uiModel.pomodoroDuration
            cBox2BreakTimeSpan.model.selectedItem = uiModel.breakDuration
            cBox2longBreakTimeSpan.model.selectedItem = uiModel.longBreakDuration
            count2Pomodoros.model.selectedItem = uiModel.longBreakFrequency
            multiText2NotificationMessages.text = uiModel.notificationMessages
            cBox2ringNotification.isSelected = uiModel.isOpenRing
            cBox2AutoStartForNext.isSelected = uiModel.startNewPomodoroAfterBreak
        }

        startCountDownTimerForOnePomodoro(index)

        updatingUI = false
    }

    private fun startCountDownTimerForOnePomodoro(countDownSecond: Int) {
        var newSecond = countDownSecond * 60
        fixedRateTimer(startAt = Calendar.getInstance().time, period = 1000L) {
            basicSettingsWindowFrom!!.TimerPresentation.text =
                (TimeUtils.convertSecondsToMinuitString(newSecond--))
            if (newSecond < 0) this.cancel()
        }
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