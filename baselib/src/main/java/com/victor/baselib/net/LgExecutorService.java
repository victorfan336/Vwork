package com.victor.baselib.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author fanwentao
 * @Description
 * @date 2018/8/8
 */
public final class LgExecutorService {

    // Common Thread Pool
    private ExecutorService executorService = new ThreadPoolExecutor(10,  Integer.MAX_VALUE,
            20L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(512), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    private LgExecutorService() {

    }

    private static final class InnerExecutorService {
        private static final LgExecutorService INSTANCE = new LgExecutorService();
    }

    public static LgExecutorService getInstance() {
        return InnerExecutorService.INSTANCE;
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            executorService.execute(runnable);
        }
    }

}
