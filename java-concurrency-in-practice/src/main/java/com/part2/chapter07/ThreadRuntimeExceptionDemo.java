package com.part2.chapter07;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 导致线程提前死亡的最主要原因是{@link RuntimeException}。
 *
 * @since 2019-10-06
 */
public class ThreadRuntimeExceptionDemo {

    private static final Logger logger = LoggerFactory.getLogger(ThreadRuntimeExceptionDemo.class);

    private static class CustomRunnable implements Runnable {
        CustomRunnable() {
            logger.info("create {}", this);
        }

        @Override
        public void run() {
            throw new RuntimeException("custom runnable");
        }
    }

    public static void main(String[] args) {
        /*
         * 1.main线程
         *
         * Exception in thread "main" java.lang.RuntimeException: main method
         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo.main(ThreadRuntimeExceptionDemo.java:16)
         *
         * @see java.lang.ThreadGroup#uncaughtException
         */
//        throw new RuntimeException("main method");

        /*
         * 2.Runnable
         *
         * Exception in thread "main" java.lang.RuntimeException: runnable
         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo.lambda$main$0(ThreadRuntimeExceptionDemo.java:25)
         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo.main(ThreadRuntimeExceptionDemo.java:27)
         */
//        Runnable r = () -> {
//            throw new RuntimeException("runnable");
//        };
//        r.run();

//        /*
//         * 3.CustomRunnable
//         *
//         * Exception in thread "main" java.lang.RuntimeException: custom runnable
//         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo$CustomRunnable.run(ThreadRuntimeExceptionDemo.java:13)
//         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo.main(ThreadRuntimeExceptionDemo.java:45)
//         */
//        Runnable r = new CustomRunnable();
//        r.run();

        /*
         * 4.ThreadFactory.newThread(Runnable)
         *
         * 2019-10-07 00:42:17,757 [pool-1-thread-1] ERROR c.p.c.LoggerUncaughtExceptionHandler - [] [] [] Thread terminated with exception: pool-1-thread-1
         * java.lang.RuntimeException: custom runnable
         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo$CustomRunnable.run(ThreadRuntimeExceptionDemo.java:19)
         * 	at java.lang.Thread.run(Thread.java:748)
         */
//        ThreadFactory threadFactory = new CustomThreadFactory(null);
//        Thread t = threadFactory.newThread(new CustomRunnable());
//        t.start();

        /*
         * 5.Executor.execute(Runnable)
         *
         * 2019-10-07 00:42:17,759 [custom-thread-1] ERROR c.p.c.LoggerUncaughtExceptionHandler - [] [] [] Thread terminated with exception: custom-thread1
         * java.lang.RuntimeException: custom runnable
         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo$CustomRunnable.run(ThreadRuntimeExceptionDemo.java:19)
         * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         * 	at java.lang.Thread.run(Thread.java:748)
         */
        ThreadFactory threadFactory = new CustomThreadFactory("custom-thread-");
        ExecutorService executor = new ThreadPoolExecutor(3, 10, 0L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), threadFactory);
//        executor.execute(new CustomRunnable());

        /*
         * 6.ExecutorService.submit(Runnable)
         *
         * 如果一个由`submit`提交的任务由于抛出了异常而结束，那么这个异常将被`Future.get()`封装在`ExecutionException`中重新抛出。
         *
         * 2019-10-07 01:04:07,200 [main] ERROR c.p.c.ThreadRuntimeExceptionDemo - [] [] [] future get fail
         * java.util.concurrent.ExecutionException: java.lang.RuntimeException: custom runnable
         * 	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
         * 	at java.util.concurrent.FutureTask.get(FutureTask.java:206)
         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo.main(ThreadRuntimeExceptionDemo.java:102)
         * Caused by: java.lang.RuntimeException: custom runnable
         * 	at com.part2.chapter07.ThreadRuntimeExceptionDemo$CustomRunnable.run(ThreadRuntimeExceptionDemo.java:31)
         * 	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
         * 	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         * 	at java.lang.Thread.run(Thread.java:748)
         */
        Future<String> future = executor.submit(new CustomRunnable(), "");
        try {
            future.get(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("future get fail for thread interrupt", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException | TimeoutException e) {
            logger.error("future get fail", e);
        }
    }
}
