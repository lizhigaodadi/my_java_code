package com.lzg.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockDemo {
    public static void main(String[] args) throws InterruptedException {
        //测试阻塞队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        //会报错的部分
//        queue.add("a");
//        queue.add("b");
//        queue.add("c");
//        queue.element();

        //不会报错的部分
//        queue.offer("a");
//        queue.offer("b");
//        queue.offer("c");
//        System.out.println(queue.peek());

        //会阻塞的部分
//        queue.put("a");
//        queue.put("a");
//        queue.put("a");
//        queue.put("a");

        //可以控制阻塞时间的
        queue.offer("a",3, TimeUnit.SECONDS);
        queue.offer("a",3, TimeUnit.SECONDS);
        queue.offer("a",3, TimeUnit.SECONDS);
        queue.offer("a",3, TimeUnit.SECONDS);



    }
}
