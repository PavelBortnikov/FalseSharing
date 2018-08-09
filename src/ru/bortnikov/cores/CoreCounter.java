package ru.bortnikov.cores;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoreCounter {

    private static final int THREAD_COUNT = 50;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        final  CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            int finalI = i;
            pool.submit(() -> {
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException e) { /*ignore*/ }

                long startTime = System.nanoTime();
                for (int k = 0; k < 100_000_000; k = k + 2)
                    k--;
                long finishTime = System.nanoTime();
                System.out.println((finishTime - startTime));

            });
        }

        Thread.sleep(1000);
        System.out.println("dsafsad");
        pool.shutdownNow();
    }
}
