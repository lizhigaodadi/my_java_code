package com.lzg.ft;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo1 {
    public static void main(String[] args) {
        //测试未来任务接口
        FutureTask<Integer> ft1 = new FutureTask(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                int sum = 0;
                for (int i = 0; i < 50; i++) {
                    sum+=i;
                }
                return sum;
            }
        });
        FutureTask<Integer> ft2 = new FutureTask(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                int sum = 0;
                for (int i=50;i<100;i++) {
                    sum+=i;
                }
                return sum;
            }
        });

        //开始新创建线程
        new Thread(ft1,"a").start();
        new Thread(ft2,"b").start();

        while (!ft1.isDone()&&ft2.isDone()) System.out.println("is waiting~~~~");

        try {
            System.out.println("结果为" + (ft1.get() + ft2.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
