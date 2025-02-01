package com.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class DownloadManager {
    public static void download(String urlString,String outputFolder, String outputFile) throws IOException {

        initialOps ops = getInitialOps(urlString, outputFolder, outputFile);

        try(ReadableByteChannel readableByteChannel = Channels.newChannel(ops.url().openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(ops.outputFileLocation());){

            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static initialOps getInitialOps(String urlString, String outputFolder, String outputFile) throws MalformedURLException {
        String outputFileLocation = outputFolder + "/" + outputFile;
        urlString = Utils.cleanUrl(urlString);

        if (!urlString.matches("^https?://[^/]+(?:/[^/]+)*/?$")){
            System.out.println("Invalid URL");
            throw new MalformedURLException("Invalid URL");
        }

        // Utils.checkOutputFile(outputFileLocation);

        URL url = URI.create(urlString).toURL();
        return new initialOps(outputFileLocation, url);
    }

    private record initialOps(String outputFileLocation, URL url) {
    }

    public static void multithreadedDownload(String urlString, String outputFolder, String outputFile, int numThreads) throws IOException {
        initialOps ops = getInitialOps(urlString, outputFolder, outputFile);

        try (ExecutorService executor = newFixedThreadPool(numThreads);
             RandomAccessFile file = new RandomAccessFile(ops.outputFileLocation(), "rw");
        ) {

            URL url = ops.url();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            long fileSize = connection.getContentLengthLong();
            // connection.disconnect();

            long chunkSize = fileSize / numThreads;
            for (int i = 0; i < numThreads; i++) {
                long start = i * chunkSize;
                long end = (i == numThreads - 1) ? fileSize : (i + 1) * chunkSize;

                executor.submit(() -> {
                    try {
                        downloadChunk(ops, file, start, end, connection);
                    } catch (IOException ex) {
                    }
                });
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void downloadChunk(initialOps ops, RandomAccessFile file, long start, long end, HttpURLConnection connection) throws IOException {
        connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(connection.getInputStream())) {
            file.seek(start);
            file.getChannel().transferFrom(readableByteChannel, start, end - start);
        }
        connection.disconnect();
    }

}
