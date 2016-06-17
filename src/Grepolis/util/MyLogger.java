package Grepolis.util;


import Grepolis.GUI.LogPanel;
import Grepolis.Town;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.*;

/**
 * @Author Brandon
 * Created by Brandon on 11/6/2015.
 * Time: 2:12 AM
 */
public class MyLogger {

    private static Logger logger;

    public MyLogger() {
        logger = Logger.getLogger(MyLogger.class.getName());
        logger.setUseParentHandlers(false);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            public String format(LogRecord record) {
                StringBuilder sb = new StringBuilder();


                if (!record.getLevel().equals(Level.INFO)) {
                    sb.append(record.getLevel().getLocalizedName());
                    sb.append(": ");
                }

                if (!Objects.equals(formatMessage(record), "\n")) {
                    ZonedDateTime zonedDateTime = ZonedDateTime.now();
                    sb.append(formatTime(zonedDateTime.getHour()))
                            .append(":").append(formatTime(zonedDateTime.getMinute()))
                            .append(":").append(formatTime(zonedDateTime.getSecond()))
                            .append(" ");
                }

                sb.append(formatMessage(record));

                if (!Objects.equals(formatMessage(record), "\n")) {
                    sb.append(System.getProperty("line.separator"));
                }

                return sb.toString();
            }
        });
        logger.addHandler(consoleHandler);
    }

    private static String formatTime(int time) {
        String currentTime = String.valueOf(time);
        if (time < 10) {
            currentTime = "0" + time;
        }
        return currentTime;
    }

    public static void log(Level level, final String msg){
        logger.log(level, msg);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder();

                if (!msg.equals("\n")) {
                    ZonedDateTime zonedDateTime = ZonedDateTime.now();
                    sb.append(formatTime(zonedDateTime.getHour()))
                            .append(":").append(formatTime(zonedDateTime.getMinute()))
                            .append(":").append(formatTime(zonedDateTime.getSecond()))
                            .append(" ");
                }

                trunkTextArea(LogPanel.getjTextArea());
                LogPanel.getjTextArea().append(sb.toString() +  msg + "\n");
            }
        });
    }

    final static int SCROLL_BUFFER_SIZE = 300;
    private static void trunkTextArea(JTextArea txtWin) {
        int numLinesToTrunk = txtWin.getLineCount() - SCROLL_BUFFER_SIZE;
        if(numLinesToTrunk > 0) {
            try {
                int posOfLastLineToTrunk = txtWin.getLineEndOffset(numLinesToTrunk - 1);
                txtWin.replaceRange("",0,posOfLastLineToTrunk);
            }
            catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void log(String msg) {
        log(Level.INFO, msg);
    }

    public static void logError(Throwable e) {
        for (StackTraceElement element : e.getStackTrace()) {
            log(Level.SEVERE, element.toString());
        }
    }

}
