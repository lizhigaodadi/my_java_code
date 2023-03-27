package com.lzg.lock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ThreadDemo4 {
    public static void main(String[] args) {
        //测试ArrayList的线程不安全属性
//        ArrayList<String> list = new ArrayList<>();
//        List<String> list = new Vector<>(); //古老的遗留类，不适合使用
//        List<String> list = Collections.synchronizedList(new ArrayList<>());   //这个也不推荐
//        List<String> list = new CopyOnWriteArrayList<>();   //这个是juc新的类，运用了写时复制，写的时候使用了锁，读未使用锁，适合多读少写的场景
//        for (int i = 0; i < 30; i++) {
//            new Thread(()->{
//                list.add(UUID.randomUUID().toString());
//                //获取list中内容
//                System.out.println(list);
//            },String.valueOf(i)).start();
//
//        }

        //测试set和map
//        Set<String> set = new HashSet<>();
//        Set<String> set = new CopyOnWriteArraySet<>();
//        for (int i = 0; i < 30; i++) {
//            new Thread(()->{
//                set.add(UUID.randomUUID().toString().substring(0,8));
//
//                System.out.println(set);
//            },String.valueOf(i)).start();
//        }

//        HashMap<String,String> map = new HashMap<>();
        ConcurrentHashMap map = new ConcurrentHashMap<String,String>();
        for (int i = 0; i < 30; i++) {
            new Thread(()-> {
                map.put(UUID.randomUUID().toString().substring(0,8),UUID.randomUUID().toString().substring(0,8));

                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
