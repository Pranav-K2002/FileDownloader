package com.downloader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = """
                
                
                https://images6.fanpop.com/image/photos/35800000/Puppy-dogs-35894603-1920-1200.jpg
                
                
                """;
        String outputFolder = "DownloadedFiles/";

        DownloadManager.download(url, outputFolder,"fiel.png");
        DownloadManager.multithreadedDownload(url, outputFolder,"fiel.png", 5);
    }
}