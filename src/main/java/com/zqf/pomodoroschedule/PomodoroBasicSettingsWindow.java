package com.zqf.pomodoroschedule;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.util.Calendar;

public class PomodoroBasicSettingsWindow {
    private JButton savePomodoroButton;
    private JButton cancelThisOperation;
    private JLabel pomodoroLength;
    private JLabel label2Pomodoros;
    private JLabel timeZone;
    private JPanel myToolWindowContent;
    private JComboBox cBox2BreakTimeSpan;
    private JComboBox cBox2PomodoroLength;
    private JLabel breakTimeSpan;
    private JLabel longBreakTimeSpan;
    private JComboBox cBox2longBreakTimeSpan;
    private JLabel notificationMessages;
    private JTextPane multiText2NotificationMessages;
    private JComboBox count2Pomodoros;

//    public PomodoroBasicSettingsWindow(ToolWindow toolWindow) {
//        cancelThisOperation.addActionListener(e -> toolWindow.hide(null));
//        savePomodoroButton.addActionListener(e -> currentDateTime());
//
//        this.currentDateTime();
//    }

        public PomodoroBasicSettingsWindow() {
        savePomodoroButton.addActionListener(e -> currentDateTime());

        this.currentDateTime();
    }

    public void currentDateTime() {
        // Get current date and time
        Calendar instance = Calendar.getInstance();
        pomodoroLength.setText(
                instance.get(Calendar.DAY_OF_MONTH) + "/"
                        + (instance.get(Calendar.MONTH) + 1) + "/"
                        + instance.get(Calendar.YEAR)
        );
        pomodoroLength.setIcon(new ImageIcon(getClass().getResource("/toolWindow/Calendar-icon.png")));
        int min = instance.get(Calendar.MINUTE);
        String strMin = min < 10 ? "0" + min : String.valueOf(min);
        label2Pomodoros.setText(instance.get(Calendar.HOUR_OF_DAY) + ":" + strMin);
        label2Pomodoros.setIcon(new ImageIcon(getClass().getResource("/toolWindow/Time-icon.png")));
        // Get time zone
        long gmt_Offset = instance.get(Calendar.ZONE_OFFSET); // offset from GMT in milliseconds
        String str_gmt_Offset = String.valueOf(gmt_Offset / 3600000);
        str_gmt_Offset = (gmt_Offset > 0) ? "GMT + " + str_gmt_Offset : "GMT - " + str_gmt_Offset;
        timeZone.setText(str_gmt_Offset);
        timeZone.setIcon(new ImageIcon(getClass().getResource("/toolWindow/Time-zone-icon.png")));
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}
