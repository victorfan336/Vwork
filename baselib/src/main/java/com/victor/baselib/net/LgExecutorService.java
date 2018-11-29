package com.victor.baselib.net;

import android.support.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author fanwentao
 * @Description
 * @date 2018/8/8
 */
public final class LgExecutorService {

    private ConcurrentHashMap<String, Future> futureMap = new ConcurrentHashMap<>();

    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    //Common Thread Pool
//    private ExecutorService pool = new ThreadPoolExecutor(3, 10,
//            10L, TimeUnit.SECONDS,
//            new LinkedBlockingQueue<Runnable>(512), Executors.defaultThreadFactory(),
//            new ThreadPoolExecutor.AbortPolicy());

    private LgExecutorService() {

    }

    private static final class InnerExecutorService {
        private static final LgExecutorService INSTANCE = new LgExecutorService();
    }

    public static LgExecutorService getInstance() {
        return InnerExecutorService.INSTANCE;
    }

    public ConcurrentHashMap<String, Future> getFutureMap() {
        return futureMap;
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            executorService.execute(runnable);
        }
    }

    /**
     * @param runnable
     * @param delay    延迟时间
     * @param timeUnit 时间单位
     */
    public void sheduler(Runnable runnable, long delay, TimeUnit timeUnit) {
        if (runnable != null) {
            executorService.schedule(runnable, delay, timeUnit);
        }
    }

    /**
     * 执行延时周期性任务
     *
     * @param runnable {@code LgExecutorSercice.JobRunnable}
     * @param initialDelay 延迟时间
     * @param period       周期时间
     * @param timeUnit     时间单位
     */
    public <T extends JobRunnable> void sheduler(T runnable, long initialDelay, long period, TimeUnit timeUnit) {
        if (runnable != null) {
            Future future = executorService.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
            futureMap.put(runnable.getJobId(), future);
        }
    }

    public static abstract class JobRunnable implements Runnable {

        private String jobId;

        public JobRunnable(@NonNull String jobId) {
            this.jobId = jobId;
        }

        /**
         * 强制终止定时线程
         */
        public void terminal() {
            try {
                Future future = LgExecutorService.getInstance().getFutureMap().remove(jobId);
                future.cancel(true);
            } finally {
                System.out.println("jobId " + jobId + " had cancel");
            }
        }

        public String getJobId() {
            return jobId;
        }

    }

}
