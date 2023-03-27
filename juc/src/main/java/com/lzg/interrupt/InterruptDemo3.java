package com.lzg.interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {

                System.out.println("hello world");
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  //再一次发起中断协商,没有这一句的话，程序会陷入死循环中
                    e.printStackTrace();
                }
            }
        }, "t1");

        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> t1.interrupt(), "t2").start();
    }


}
