package cs125.finalproject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ThreadHandler {
    private static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void addRunnable(Runnable runnable) {
        executor.submit(runnable);
    }
    public static void shutdown() {
        executor.shutdown();
        executor.shutdownNow();
    }
    public static ExecutorService getExecutor() {
        return executor;
    }
}
