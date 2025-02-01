package com.downloader;

public class Utils {
    public static String cleanUrl(String url) {
        return url.trim().replaceAll("[\n\r\t]", "");
    }

    public static void checkOutputFile(String outputFile) {
        if (outputFile == null || outputFile.isEmpty()) {
            throw new IllegalArgumentException("Output file cannot be null or empty");
        }
    }
}
