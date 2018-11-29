package com.victor.liblock.test1;

/**
 * @author fanwentao
 * @Description
 * @date 2018/7/9
 */
public class Producer extends Thread {
    private int num;

    public AbstractStorage abstractStorage;

    public Producer(AbstractStorage abstractStorage) {
        this.abstractStorage = abstractStorage;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        produce(num);
    }

    public void produce(int num) {
        abstractStorage.produce(num);
    }
}
