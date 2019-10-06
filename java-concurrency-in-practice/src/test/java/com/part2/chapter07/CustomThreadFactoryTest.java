package com.part2.chapter07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

/**
 * Test of {@link CustomThreadFactory}.
 *
 * @since 2019-10-06
 */
public class CustomThreadFactoryTest {

    @Test
    public void runtimeException() {
        ThreadFactory threadFactory = new CustomThreadFactory("custom-thread-");
        ExecutorService executor = new ThreadPoolExecutor(3, 10, 0L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), threadFactory);
        for (int i = 0; i < 3; i++) {
            executor.execute(() -> {
                throw new RuntimeException("abnormal thread termination for custom thread name");
            });
        }
        executor.shutdown();

        threadFactory = new CustomThreadFactory(null);
        executor = new ThreadPoolExecutor(3, 10, 0L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), threadFactory);
        for (int i = 0; i < 3; i++) {
            executor.execute(() -> {
                throw new RuntimeException("abnormal thread termination for default thread name");
            });
        }
        executor.shutdown();
    }
}
