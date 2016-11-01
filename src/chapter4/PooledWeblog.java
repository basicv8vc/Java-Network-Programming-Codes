package chapter4;

import sun.rmi.runtime.Log;

import java.net.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

// Require Java 7 for try-with-resources and multi-catch
public class PooledWeblog {

    private static final int NUM_THREADS = 4;

    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        Queue<LogEntry> results = new LinkedList<LogEntry>();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));) {

            for(String entry = in.readLine(); entry != null; entry = in.readLine()) {
                LookupTask task = new LookupTask(entry);
                Future<String> future = executorService.submit(task);
                LogEntry result = new LogEntry(entry, future);
                results.add(result);
            }
        }

        // Start printing the results.
        for(LogEntry result : results) {
            try {
                System.out.println(result.future.get());
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println(result.original);
            }
        }

        executorService.shutdown();

    }

    private static class LogEntry {
        String original;
        Future<String> future;

        LogEntry(String original, Future<String> future) {
            this.original = original;
            this.future = future;
        }
    }
}
