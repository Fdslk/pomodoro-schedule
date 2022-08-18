package com.zqf.pomodoroschedule;

import javax.swing.*;

public class PomodoroBasicSettingsWindow {
    private JLabel pomodoroLength;
    private JLabel label2Pomodoros;
    private JLabel pomodoroCounts;
    private JPanel myToolWindowContent;
    public JComboBox cBox2BreakTimeSpan;
    public JComboBox cBox2PomodoroLength;
    private JLabel breakTimeSpan;
    private JLabel longBreakTimeSpan;
    public JComboBox cBox2longBreakTimeSpan;
    private JLabel notificationMessages;
    public JTextPane multiText2NotificationMessages;
    public JComboBox count2Pomodoros;
    public JButton startPomodorobutton;
    private JLabel Timer;
    public JLabel TimerPresentation;

    public JPanel getContent() {
        return myToolWindowContent;
    }

}
