package com.lzg.nio;

import sun.reflect.generics.scope.Scope;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) {
        //发送图片给服务器
        String fileName = "1.txt";
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);
        SocketChannel socketChannel = null;

        //获取当前的时间
        long start = System.currentTimeMillis();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            //获取通道
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(4096);

            //写入数据
            channel.read(buffer);

            socketChannel = SocketChannel.open(inetSocketAddress);
            socketChannel.configureBlocking(false);
            int bigSmall = socketChannel.write(buffer);//发送数据

            long end = System.currentTimeMillis();
            System.out.println("发送" + bigSmall + "字节大小的文件花费了" + (end - start) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
