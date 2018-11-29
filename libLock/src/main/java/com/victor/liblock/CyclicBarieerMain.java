package com.victor.liblock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fanwentao
 * @Description
 * @date 2018/8/16
 */
public class CyclicBarieerMain {

    public static void main(String[] args) {
        int N = 5;
        ExecutorService service = Executors.newFixedThreadPool(N);
        CyclicBarrier startSignal = new CyclicBarrier(N);
        for (int i = 0; i < N; ++i) {
            service.execute(new Worker(startSignal, i));
            sleep(1000);
        }
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {
        private final CyclicBarrier startSignal;
        private int id;

        Worker(CyclicBarrier startSigna, int id) {
            startSignal = startSigna;
            this.id = id;
        }

        @Override
        public void run() {
            doWork("start thread : " + id);
            try {
                startSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            doWork("thread done" + id);
        }

        void doWork(String tag) {
            System.out.println(tag);
        }

    }
}