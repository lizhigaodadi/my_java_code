package com.lzg.threadlocal;

import com.lzg.sync.SaleTicket;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        House house = new House();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                int size = new Random().nextInt(5)+1;
                System.out.println(Thread.currentThread().getName() + "----" + size);
                for (int i1 = 0; i1 < size; i1++) {
                    house.sale();
                }
            },i+"").start();
        }

        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName()+"----"+house.getSaleCount().get());
    }
}

@Data
@NoArgsConstructor
class House {
    private ThreadLocal<Integer> saleCount = ThreadLocal.withInitial(()->0);

    public void sale() {
        this.saleCount.set(saleCount.get()+1);
    }

}
