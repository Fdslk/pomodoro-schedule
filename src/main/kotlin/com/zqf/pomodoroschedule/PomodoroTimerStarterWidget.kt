package com.zqf.pomodoroschedule

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.impl.status.TextPanel
import com.zqf.pomodoroschedule.model.BasicSettings
import com.zqf.pomodoroschedule.model.PomodoroState
import com.zqf.pomodoroschedule.model.PomodoroState.Mode
import java.awt.Graphics
import java.awt.Insets
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JComponent

class PomodoroTimerStarterWidget: StatusBarWidgetFactory {
    override fun getId() = "Pomodoro.schedule"

    override fun getDisplayName() = "Pomodoro.schedule"

    override fun isAvailable(project: Project) = true

    override fun createWidget(project: Project) = PomodoroWidget()

    override fun disposeWidget(widget: StatusBarWidget) = widget.dispose()

    override fun canBeEnabledOn(statusBar: StatusBar) = true
}
class PomodoroWidget : CustomStatusBarWidget, StatusBarWidget.Multiframe {
    private lateinit var statusBar: StatusBar
    private val model = service<PomodoroState>()
    private val panelWithIcon = TextPanelWithIcon()

    init {
        val basicSettings = service<BasicSettings>()
        updateWidgetPanel(model, panelWithIcon, basicSettings.isShowTimeInToolbarWidget)
    }

    override fun dispose() {
        this.dispose()
    }

    override fun ID() = "Pomodoro.schedule"

    override fun install(statusBar: StatusBar) {
        this.statusBar = statusBar
    }

    override fun copy() = PomodoroWidget()

    override fun getComponent(): JComponent = panelWithIcon

    private fun Long.formatted(): String {
        val formattedMinutes = String.format("%02d", this)
        val formattedSeconds = String.format("%02d", 0)
        return "$formattedMinutes:$formattedSeconds"
    }

    private fun nextActionName(model: PomodoroState) = when (model.state.currentMode) {
        Mode.Run -> UIBundle.message("statuspanel.stop")
        Mode.Break -> UIBundle.message("statuspanel.stop_break")
        Mode.Stop -> UIBundle.message("statuspanel.start")
    }

    private fun updateWidgetPanel(model: PomodoroState, panelWithIcon: TextPanelWithIcon, showTimeInToolbarWidget: Boolean) {
        panelWithIcon.text = if (showTimeInToolbarWidget) model.leftPomodorosTimeSpan.formatted() else ""
        panelWithIcon.toolTipText = UIBundle.message("statuspanel.widget_tooltip", nextActionName(model))
        panelWithIcon.icon = when (model.state.currentMode) {
            Mode.Run ->  pomodoroIcon
            Mode.Break -> pomodoroBreakIcon
            Mode.Stop -> pomodoroStoppedIcon
        }
        panelWithIcon.repaint()
    }


    companion object {
        private val pomodoroIcon = loadIcon("/pomodoro.png")
        private val pomodoroStoppedIcon = loadIcon("/pomodoro.png")
        private val pomodoroBreakIcon = loadIcon("/pomodoro.png")

        private fun loadIcon(filePath: String) = ImageIcon(PomodoroWidget::class.java.getResource(filePath))
    }
}

internal class TextPanelWithIcon: TextPanel() {
    private val gap = 2

    var icon: Icon? = null

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (text == null) return

        icon?.paintIcon(
            this, g,
            insets.left - gap - iconWidth(),
            bounds.height / 2 - iconWidth() / 2
        )
    }

    override fun getInsets(): Insets {
        val insets = super.getInsets()
        insets.left += iconWidth() + gap * 2
        return insets
    }

    private fun iconWidth() = icon?.iconWidth ?: 0
}
