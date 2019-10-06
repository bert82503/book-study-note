package com.part2.chapter07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常处理器如何处理未捕获异常，
 * 将一个错误信息以及相应的栈追踪信息写入应用程序日志中。
 *
 * @since 2019-10-06
 */
final class LoggerUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUncaughtExceptionHandler.class);

    LoggerUncaughtExceptionHandler() {
        logger.info("create {}", this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("Thread terminated with exception: " + t.getName(), e);
    }
}
