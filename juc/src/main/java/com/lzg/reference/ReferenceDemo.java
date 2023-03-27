package com.lzg.reference;

public class ReferenceDemo {
    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        myObject = null;
        System.gc();   //唤醒gc线程进行垃圾回收

    }
}

class MyObject {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("myObject被垃圾回收了");
    }
}
