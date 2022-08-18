package com.zqf.pomodoroschedule;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class PomodoroBasicSettingsWindow {
    private JLabel pomodoroLength;
    private JLabel label2Pomodoros;
    private JLabel timeZone;
    private JPanel myToolWindowContent;
    public JComboBox cBox2BreakTimeSpan;
    public JComboBox cBox2PomodoroLength;
    private JLabel breakTimeSpan;
    private JLabel longBreakTimeSpan;
    public JComboBox cBox2longBreakTimeSpan;
    private JLabel notificationMessages;
    public JTextPane multiText2NotificationMessages;
    public JComboBox count2Pomodoros;
    private JButton StartPomodorobutton;
    private JLabel Timer;
    private JLabel TimerPresentation;

    public JPanel getContent() {
        return myToolWindowContent;
    }

}
