package com.victor.liblock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fanwentao
 * @Description
 * @date 2018/8/30
 */
public class SemaphoreMain {

    public static class ResourceSemaphore {

        private int N = 5;
        private ReentrantLock lock;
        private List<String> footList = new ArrayList<>();
        Semaphore semaphore = new Semaphore(N,true);

        public ResourceSemaphore() {
            for (int i = 0; i < N; i++) {
                footList.add("资源" + i);
            }
        }

        public String getResource() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("获取到资源了");
            return "";
        }

    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);




    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {
        private final Semaphore startSignal;
        private int id;

        Worker(Semaphore startSigna, int id) {
            startSignal = startSigna;
            this.id = id;
        }

        @Override
        public void run() {
            log("start thread : " + id);
            try {
                startSignal.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startSignal.release();
            log("thread done" + id);
        }



    }

    public static void log(String tag) {
        System.out.println(tag);
    }
}
