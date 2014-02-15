package org.entityapi.utils;

import org.entityapi.EntityAPICore;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorReporter {

    private static final String DATE = new SimpleDateFormat("yyyy-MM-dd kk:mm Z").format(new Date());

    private ErrorReporter() {}

    public static PastebinReporter.Paste generatePaste(Exception exception) {
        PastebinReporter.Paste paste = new PastebinReporter.Paste("-----[EntityAPI - ErrorReport - " + DATE + "]-----");
        addSystemInfo(paste);
        paste.appendLine("\n");
        addStacktrace(paste, exception);
        paste.appendLine("\n");
        paste.appendLine("------------------------------");

        return null;
    }

    protected static void addSystemInfo(PastebinReporter.Paste paste) {
        paste.appendLine("----------System INFO----------")
                .appendLine("EntityAPI version: " + EntityAPICore.getVersion())
                .appendLine("Server version: " + Bukkit.getVersion())
                .appendLine("Server brand: " + EntityAPICore.SERVER.getName())
                .appendLine("Java Version: " + System.getProperty("java.vendor") + " " + System.getProperty("java.version"))
                .appendLine("OS: " + System.getProperty("os.name") + " v:" + System.getProperty("os.version"))
                .appendLine("Java Version: " + System.getProperty("java.vendor") + " " + System.getProperty("java.version"));
    }

    protected static void addStacktrace(PastebinReporter.Paste paste, Exception e) {
        paste.appendLine("----------Error/Exception----------")
                .appendLine("Exception message: " + e.getMessage())
                .appendLine("Exception cause: " + e.getCause().getMessage())
                .appendLine("Exception: " + getStacktraceAsString(e));
    }

    protected static String getStacktraceAsString(Exception e) {
        StringBuilder result = new StringBuilder();
        result.append(e.toString());
        String NEW_LINE = System.getProperty("line.separator");
        result.append(NEW_LINE);

        for (StackTraceElement element : e.getStackTrace()){
            result.append(element);
            result.append(NEW_LINE);
        }
        return result.toString();
    }
}
