package com.lzg.atomic;

import lombok.*;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class MyAtomicStampDemo {

    public static void main(String[] args) {
        Book javaBook = new Book("java并发编程艺术",100.0);
        AtomicStampedReference<Book> book = new AtomicStampedReference<>(javaBook,1);
        Book mysqlBook = new Book("mysql从入门到删库",200.0);
        boolean b = book.compareAndSet(javaBook, mysqlBook, book.getStamp(), book.getStamp() + 1);
        System.out.println(b+"\t"+book.getReference());

    }
}

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
class Book {
    private String name;
    private double price;
}
