package Grepolis.util;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
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

    private String formatTime(int time) {
        String currentTime = String.valueOf(time);
        if (time < 10) {
            currentTime = "0" + time;
        }
        return  currentTime;
    }

    public static void log(Level level, String msg){
        logger.log(level, msg);
//        System.out.println(msg);
    }

    public static void log(String msg) {
        log(Level.INFO, msg);
    }

    public static void logError(Throwable e) {
        for (StackTraceElement element : e.getStackTrace()) {
            log(Level.SEVERE, element.toString());
        }
    }

    public static void main(String args[]) {
        new MyLogger();
        log("Testing 1");
        log("\n");
        log(Level.WARNING, "Testing 2");
    }
}
