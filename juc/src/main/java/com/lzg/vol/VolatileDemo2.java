package com.lzg.vol;

import java.util.concurrent.TimeUnit;

public class VolatileDemo2 {
    private static volatile int i = 0;
    public static void main(String[] args) {
        //检验volatile的原子性
        for (int i1 = 0; i1 < 100; i1++) {
            add();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i);
    }

    public static void add() {
        new Thread(()->{
            for (int j=0;j<1000;j++) {
                i++;
            }
        }).start();
    }

}
