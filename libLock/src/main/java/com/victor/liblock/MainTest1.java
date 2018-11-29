package com.victor.liblock;

import java.util.Random;

public class MainTest1 {

    public Token token = null;
    WorkThread workthread;

    public MainTest1() {
        token = new Token();
        workthread = new MainTest1.WorkThread();
        workthread.start();
    }

    private class Token {
        private String flag;

        public Token() {
            setFlag(null);
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getFlag() {
            return flag;
        }
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            Random r = new Random();
            while (true) {
                synchronized (token) {
                    try {
                        token.wait();
                        VL.e("the value is :" + token.getFlag());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                VL.e("once end!!");
            }
        }
    }

    /*public static void main(String... args) {
        MainTest1 object = new MainTest1();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep((r.nextInt(9) + 1) * 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object.token) {
                object.token.setFlag("victor : " + Integer.toString(i));
                object.token.notify();
                VL.e("current index " + Integer.toString(i));
            }
        }
    }*/
}

