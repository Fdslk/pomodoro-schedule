package com.zqf.pomodoroschedule

import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.PlatformDataKeys.CONTEXT_COMPONENT
import com.intellij.openapi.actionSystem.impl.AsyncDataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.JBPopupFactory.ActionSelectionAid.SPEEDSEARCH
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.impl.status.TextPanel
import com.intellij.ui.awt.RelativePoint
import com.zqf.pomodoroschedule.model.BasicSettings
import com.zqf.pomodoroschedule.model.PomodoroModel
import com.zqf.pomodoroschedule.model.PomodoroState
import com.zqf.pomodoroschedule.model.PomodoroState.Mode
import com.zqf.pomodoroschedule.model.time.Duration
import com.zqf.pomodoroschedule.model.time.Time
import java.awt.Graphics
import java.awt.Insets
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
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
    private val model = service<PomodoroService>().model
    private val panelWithIcon = TextPanelWithIcon()

    init {
        val basicSettings = service<BasicSettings>()
        updateWidgetPanel(model, panelWithIcon, basicSettings.isShowTimeInToolbarWidget)

        model.addListener(this, object : PomodoroModel.Listener {
            override fun onStateChange(state: PomodoroState, wasManuallyStopped: Boolean) {
                ApplicationManager.getApplication().invokeLater { updateWidgetPanel(model, panelWithIcon, basicSettings.isShowTimeInToolbarWidget) }
            }
        })

        panelWithIcon.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e == null) return
                if (e.isAltDown) {
                    val popup = JBPopupFactory.getInstance().createActionGroupPopup(
                        null,
                        DefaultActionGroup(listOf(StartOrStopPomodoro(), ResetPomodorosCounter())),
                        MapDataContext(mapOf(CONTEXT_COMPONENT.name to e.component)),
                        SPEEDSEARCH,
                        true
                    )
                    val dimension = popup.content.preferredSize
                    val point = Point(0, -dimension.height)
                    popup.show(RelativePoint(e.component, point))
                } else if (e.button == MouseEvent.BUTTON1) {
                    model.onUserSwitchToNextState(Time.now())
                }
            }

            override fun mouseEntered(e: MouseEvent?) {
                statusBar.info = UIBundle.message("statuspanel.tooltip", nextActionName(model), model.state.pomodorosAmount)
            }

            override fun mouseExited(e: MouseEvent?) {
                statusBar.info = ""
            }
        })
    }

    private class MapDataContext(private val map: Map<String, Any?> = HashMap()) : AsyncDataContext {
        override fun getData(dataId: String) = map[dataId]
    }

    override fun dispose() {
        model.removeListener(this)
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

    private fun nextActionName(model: PomodoroModel) = when (model.state.currentMode) {
        Mode.Run -> UIBundle.message("statuspanel.stop")
        Mode.Break -> UIBundle.message("statuspanel.stop_break")
        Mode.Stop -> UIBundle.message("statuspanel.start")
    }

    private fun updateWidgetPanel(model: PomodoroModel, panelWithIcon: TextPanelWithIcon, showTimeInToolbarWidget: Boolean) {
        panelWithIcon.text = if (showTimeInToolbarWidget) model.leftPomodorosTimeSpan.formatted() else ""
        panelWithIcon.toolTipText = UIBundle.message("statuspanel.widget_tooltip", nextActionName(model))
        panelWithIcon.icon = when (model.state.currentMode) {
            Mode.Run ->  pomodoroIcon
            Mode.Break -> pomodoroBreakIcon
            Mode.Stop -> pomodoroStoppedIcon
        }
        panelWithIcon.repaint()
    }

    private fun Duration.formatted(): String {
        val formattedMinutes = String.format("%02d", minutes)
        val formattedSeconds = String.format("%02d", (this - Duration(minutes)).seconds)
        return "$formattedMinutes:$formattedSeconds"
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
