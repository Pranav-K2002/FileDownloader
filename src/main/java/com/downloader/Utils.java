package com.downloader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String cleanUrl(String url) {
        return url.trim().replaceAll("[\n\r\t]", "");
    }

    public static void checkOutputFile(String outputFile) {
        if (outputFile == null || outputFile.isEmpty()) {
            throw new IllegalArgumentException("Output file cannot be null or empty");
        }
    }

    public static String getCurrentTimeStamp() {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-ms");//dd/MM/yyyy
    Date now = new Date();
    String strDate = sdfDate.format(now);
    return strDate;
}
}
