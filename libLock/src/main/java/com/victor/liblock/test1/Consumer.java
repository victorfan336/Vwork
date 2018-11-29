package com.victor.liblock.test1;

/**
 * @author fanwentao
 * @Description
 * @date 2018/7/9
 */
public class Consumer extends Thread {
    private int num;

    private AbstractStorage abstractStorage1;

    public Consumer(AbstractStorage abstractStorage1) {
        this.abstractStorage1 = abstractStorage1;
    }

    @Override
    public void run() {
        consume(num);
    }

    public void consume(int num) {
        abstractStorage1.consume(num);
    }

    public void setNum(int num) {
        this.num = num;
    }
}
