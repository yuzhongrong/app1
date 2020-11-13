package com.blockchain.common.base.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: qinhui
 * @date: 2020/9/18 10:08
 */
public class ExeptionPrintUtils {
    public static String getExceptionStackTrace(Throwable anexcepObj) {
        StringWriter sw = null;
        PrintWriter printWriter = null;
        try {
            if (anexcepObj != null) {
                sw = new StringWriter();
                printWriter = new PrintWriter(sw);
                anexcepObj.printStackTrace(printWriter);
                printWriter.flush();
                sw.flush();
                return sw.toString();
            } else
                return null;
        } finally {

            try {
                if (sw != null)
                    sw.close();
                if (printWriter != null)
                    printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
