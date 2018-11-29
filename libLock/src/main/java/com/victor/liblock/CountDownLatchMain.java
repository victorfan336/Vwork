package com.victor.liblock;

import java.util.concurrent.CountDownLatch;

/**
 * @author fanwentao
 * @Description
 * @date 2018/8/16
 */
public class CountDownLatchMain {

    public static void main(String[] args) {
        int N = 5;
        System.out.println("the start");
        CountDownLatch startSignal = new CountDownLatch(N);
        for (int i = 0; i < N; ++i) {
            new Thread(new Worker(startSignal)).start();
        }

        try {
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("the end");
    }

    static class Worker implements Runnable {
        private final CountDownLatch startSignal;

        Worker(CountDownLatch startSignal2) {
            startSignal = startSignal2;
        }

        @Override
        public void run() {
            startSignal.countDown();
            doWork("start", startSignal.getCount());
        }

        void doWork(String tag, long value) {
            System.out.println(tag + " : " + value);
        }

    }
}