package com.lzg.atomic;

import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicFieldReferenceUpdaterDemo {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                MyVar.init(myVar);
            },i+"").start();
        }
    }


}

@NoArgsConstructor
class MyVar {
    public volatile Boolean isInit = Boolean.FALSE;
    private static AtomicReferenceFieldUpdater<MyVar,Boolean> atomicReferenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class,Boolean.class,"isInit");

    public static void init(MyVar myVar) {
        System.out.println(Thread.currentThread().getName() + "正在尝试初始化");
        if (atomicReferenceFieldUpdater.compareAndSet(myVar,Boolean.FALSE,Boolean.TRUE)){
            System.out.println(Thread.currentThread().getName()+"初始化成功");
        } else {
            System.out.println(Thread.currentThread().getName() + "初始化失败");
        }
    }
}
