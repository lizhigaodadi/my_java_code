package com.lzg.sync;

public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket(30);
        for (int i=0;i<3;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for  (int j=0;j<40;j++) ticket.sale();
                }
            }).start();
        }
        System.out.println("main over: "+Thread.currentThread().getName());
    }
}


class Ticket {
    private int number;
    public Ticket(int number) {this.number=number;}

    public synchronized void sale() {   //加上锁
        if (number > 0){
            System.out.println(Thread.currentThread().getName()+"卖出去了1张票，还剩余"+(--number)+"票");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
