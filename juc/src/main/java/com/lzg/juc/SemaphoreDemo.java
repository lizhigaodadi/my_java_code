package com.lzg.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        //准备三个信号量
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i <= 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢占到了停车位");
                    //随机停一段时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放资源
                    System.out.println(Thread.currentThread().getName() + "离开了停车位");
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
