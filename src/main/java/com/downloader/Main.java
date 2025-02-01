package com.downloader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] urls = {
                "https://d33wubrfki0l68.cloudfront.net/63555732651b6f496994bbb87f68b8ff5f24466c/39d38/static/ef5d423de0a944e9b27a131e81cf2dab/6a068/java-format-date-time.jpg",
                "https://i.ytimg.com/vi/qc930nafWdM/maxresdefault.jpg",
                "https://www.golinuxcloud.com/wp-content/uploads/java_datetime.jpg",
                "https://1.bp.blogspot.com/-MvGZOiLHSDQ/YPPpM-_3bZI/AAAAAAAAoxo/CQNiyZpwRSsGpE-HhKduRWWRv-o3RdkwACLcBGAsYHQ/w628-h353/Date%2Bto%2BLocalDateTime%2Bin%2BJava%2B8%2Bwith%2Bexample.png"
            };

        String outputFolder = "downloadedFiles/";
        int NUM_THREADS = 5;

        long startTime = System.currentTimeMillis();

        for(String url : urls){
            // DownloadManager.download(url, outputFolder, url.substring(url.lastIndexOf('/') + 1));
            DownloadManager.multithreadedDownload(url, outputFolder,url.substring(url.lastIndexOf('/') + 1), NUM_THREADS);

        }
        System.out.println("Time taken for multi: " + (System.currentTimeMillis() - startTime) + "ms");

        startTime = System.currentTimeMillis();
    
        for(String url : urls){
            // DownloadManager.multithreadedDownload(url, outputFolder,url.substring(url.lastIndexOf('/') + 1), NUM_THREADS);
            DownloadManager.download(url, outputFolder, url.substring(url.lastIndexOf('/') + 1));

        }
        System.out.println("Time taken for normal: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}