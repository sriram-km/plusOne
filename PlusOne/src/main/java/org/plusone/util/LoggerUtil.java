package org.plusone.util;/*
 * @author Sri Ram (zt-753)
 */

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggerUtil {
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        return exceptionAsString;
    }
}
