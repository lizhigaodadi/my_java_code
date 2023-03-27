package com.lzg.vol;

import java.util.concurrent.TimeUnit;

public class VolatileDemo1 {
    private static volatile boolean flag = true;
    public static void main(String[] args) {
        //验证可见性

        new Thread(()->{
            while (flag) {
                System.out.println("hello world");
//                try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(()->{
            flag = false;
            System.out.println("变量修改完成,flag:"+flag);
        }).start();


    }
}
