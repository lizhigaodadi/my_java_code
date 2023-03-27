package com.lzg.sync;

public class ThreadDemo1 {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(()->{
            for (int i=0;i<10;i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"减少线程").start();

        new Thread(()->{
            for (int i=0;i<10;i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"增加线程").start();
    }
}

class Share {
    private int number = 1;
    public synchronized void incr() throws InterruptedException {
        if (number == 1) this.wait();  //判断
        number++;   //工作
        System.out.println(Thread.currentThread().getName() +":"+ number);
        this.notifyAll();   //等待
    }
    public synchronized void decr() throws InterruptedException {
        if (number == 0) this.wait();
        number--;
        System.out.println(Thread.currentThread().getName() +":"+ number);
        this.notifyAll();

    }

}
