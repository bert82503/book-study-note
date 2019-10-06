package com.part2.chapter07;

/**
 * 典型的线程池工作者线程结构。
 * <p>
 * 如何在线程池内部构建一个工作者线程。
 * 如果任务抛出了一个未检查异常，那么它将使线程终结，但会首先通知框架该线程已经终结。
 * 然后，框架可能会用新的线程来代替这个工作者线程，也可能不会，因为线程池正在关闭，
 * 或者当前已有足够多的线程能满足需求。
 *
 * @since 2019-10-06
 */
public final class TaskWorkerThread extends Thread {

    @Override
    public void run() {
        Throwable thrown = null;
        try {
            while (!isInterrupted()) {
                // 运行任务(从工作队列中获取任务)
//                runTask(getTaskFromWorkQueue());
            }
        } catch (Throwable e) {
            thrown = e;
        } finally {
            // 该线程已经终止
//            threadExited(this, thrown);
        }
    }
}
