package com.zqf.pomodoroschedule

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JTextField

class DataDialogWrapper: DialogWrapper(true) {

    val panel = JPanel(GridBagLayout())
    val txtMode = JTextField()
    val txtUserName = JTextField()
    val txtUserPWD = JPasswordField()

    init {
        init()
        title = this.javaClass.toGenericString()
    }

    override fun createCenterPanel(): JComponent? {
        val gridBag = GridBag()
            .setDefaultInsets(Insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
            .setDefaultWeightX(1.0)
            .setDefaultFill(GridBagConstraints.HORIZONTAL)

        panel.add(label("mode"), gridBag.nextLine().next().weightx(0.2))
        panel.add(txtMode, gridBag.nextLine().next().weightx(0.8))
        panel.add(label("username"), gridBag.nextLine().next().weightx(0.2))
        panel.add(txtUserName, gridBag.nextLine().next().weightx(0.8))
        panel.add(label("password"), gridBag.nextLine().next().weightx(0.2))
        panel.add(txtUserPWD, gridBag.nextLine().next().weightx(0.8))

        return panel
    }

    private fun label(text: String): JComponent {
        val label = JBLabel(text)
        label.componentStyle = UIUtil.ComponentStyle.SMALL
        label.fontColor = UIUtil.FontColor.BRIGHTER
        label.border = JBUI.Borders.empty(0, 5, 2, 0)
        return label
    }
}