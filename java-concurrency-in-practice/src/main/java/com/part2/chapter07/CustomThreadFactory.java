package com.part2.chapter07;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义的线程工厂，定制线程名称前缀、守护线程状态、线程的优先级、未捕获异常处理器。
 *
 * @since 2019-10-06
 * @see java.util.concurrent.Executors#defaultThreadFactory()
 */
public final class CustomThreadFactory implements ThreadFactory {

    private static final Logger logger = LoggerFactory.getLogger(CustomThreadFactory.class);
    /** 线程池序列号 */
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

    /** 线程序列号 */
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    /** 线程名称前缀 */
    private final String namePrefix;

    static {
        // 为所有线程的未捕获异常指定同一个异常处理器(JVM层面)
        Thread.setDefaultUncaughtExceptionHandler(new LoggerUncaughtExceptionHandler());
    }

    public CustomThreadFactory(String namePrefix) {
        if (namePrefix == null || namePrefix.isEmpty()) {
            this.namePrefix = "pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        } else {
            this.namePrefix = namePrefix;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Thread#toString()
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
        // 守护线程
        if (!t.isDaemon()) {
            t.setDaemon(true);
        }
        // 线程的优先级
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        // 为线程池中的所有线程设置一个未捕获异常处理器
        t.setUncaughtExceptionHandler(new LoggerUncaughtExceptionHandler());
        logger.info("create thread: {}", t);
        return t;
    }
}
