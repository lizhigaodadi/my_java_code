package com.lzg.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

//聊天室的客户端代码
public class TalkClient {
    private static Selector selector;
    private static SocketChannel channel;
    private static String IP = "127.0.0.1"; //ip
    private static int port = 6667;  //端口号
    private static ByteBuffer buffer;

    //连接服务器代码
    private static void connectToServer() {
        try {
            //初始化
            selector = Selector.open();
            channel = SocketChannel.open(new InetSocketAddress(IP,port));
            buffer = ByteBuffer.allocate(1024);


            //绑定行为和设置非阻塞
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ,buffer);

//            //开始进行连接
//            channel.connect(new InetSocketAddress(IP,port));
//            if (!channel.isConnected()) {
//                System.out.println("正在连接服务器");
//                while (!channel.finishConnect()) {
//                    System.out.println(".");
//                }
//            }

            System.out.println("成功连接到服务器");

            while (true) {
                int select = selector.select(1000);
                if (select > 0) {
                    //获取keys集合
                    System.out.println("准备就绪");
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();

                        if (key.isReadable()) {
                            //有人发来了消息,规定传入buffer
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer currentBuffer = (ByteBuffer) key.attachment();

                            //读取数据
                            channel.read(currentBuffer);
                            System.out.println(new String(currentBuffer.array()));
                        }

                        keyIterator.remove();
                    }

                    //为各种事件提供相应解决方案

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sendMessage(String message) {
        try {
            System.out.println("成功发送一条消息");
            channel.write(buffer.put(message.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //多开一个线程来连接服务器
        new Thread() {
            @Override
            public void run() {
                connectToServer();
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        String message = "";
        while (true) {
            message = scanner.nextLine();
            if ("exit".equals(message)) {
                break;
            }
            sendMessage(message);
        }
    }


}
