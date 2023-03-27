package com.lzg.buffer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception {
        //获取ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //获取选择器
        Selector selector = Selector.open();

        //关联ServerSocketChannel和Selector
        serverSocketChannel.bind(new InetSocketAddress(6666));   //绑定6666端口号
        //设置为非阻塞型
        serverSocketChannel.configureBlocking(false);

        //开始注册
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);   //绑定监听连接事件

        //while循环不断的进行非阻塞型监听事件的发生
        while (true) {
            if (selector.select(2000) == 0) {
                //表示当前2s内没有事件发生
                System.out.println("当前2s内没有任何事件发生");
                continue;  //继续监听
            }
            System.out.println("有事件发生了");
            //进行到这里说明有事件发生获取该事件
            Set<SelectionKey> keys = selector.keys();
            //获取迭代器
            Iterator<SelectionKey> keyIterator = keys.iterator();

            //获取所有的Selector

            while (keyIterator.hasNext()) {  //分别处理每一个事件
                //获取当前事件的类型
                SelectionKey next = keyIterator.next();

                //判断该key是什么类型，并做出相应的处理
                if (next.isAcceptable()) {
                    //说明是刚刚连接上的事件
                    //给他绑定一个socketChannel，因为这个时候已经有事件发生了，所以accept方法并不会阻塞
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //打印输出通道
                    System.out.println("socketChannel: " + socketChannel + "hashCode: " + socketChannel.hashCode());
                    //设置通道为非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("有客户端连接上来了");
                    //相互绑定,绑定缓冲区和selector
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }

                if (next.isReadable()) {
                    System.out.println("有数据需要读取了");
                    //读取信息的事件
                    //获取缓冲区和通道
                    ByteBuffer attachment = (ByteBuffer) next.attachment();
                    SocketChannel channel = (SocketChannel) next.channel();
                    //读取数据
                    channel.read(attachment);
                    //打印信息
                    System.out.println("form 客户端" + new String(attachment.array()));
                }

                //最后将key从迭代器中删除
                keyIterator.remove();
            }
        }


    }
}
