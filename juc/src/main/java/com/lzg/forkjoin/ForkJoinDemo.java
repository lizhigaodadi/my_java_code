package com.lzg.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //测试分支合并框架
        MyTask myTask = new MyTask(1, 100);
        myTask.fork();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);

        System.out.println(forkJoinTask.get());

        forkJoinPool.shutdown();

    }
}


class MyTask extends RecursiveTask<Integer> {
    private final int VALUE = 10;
    private int begin;
    private int end;

    public MyTask(int begin,int end) {
        this.begin = begin;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int result = 0;
        if ((end - begin) < VALUE) {
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            //继续拆分
            int mid = (begin + end ) / 2;
            MyTask task1 = new MyTask(begin,mid);
            MyTask task2 = new MyTask(mid+1,end);

            //执行异步操作
            task1.fork();
            task2.fork();

            result = task1.join() + task2.join();  //阻塞获取调用结果
        }
        return result;
    }
}
