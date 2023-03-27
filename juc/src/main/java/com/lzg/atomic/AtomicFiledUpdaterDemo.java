package com.lzg.atomic;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicFiledUpdaterDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        BankAccount bankAccount = new BankAccount(0);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    for (int i1 = 0; i1 < 100; i1++) {
                        bankAccount.transferMoney();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        try { countDownLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(bankAccount.money);


    }
}


class BankAccount {
    public volatile int money;

    public BankAccount(Integer money) { this.money=money; }

    AtomicIntegerFieldUpdater<BankAccount> atomicIntegerFieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");

    public void transferMoney() { atomicIntegerFieldUpdater.getAndIncrement(this); }
}
