package com.lzg.buffer;

import sun.reflect.generics.scope.Scope;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class MoreBufferTest {
    public static void main(String[] args) throws Exception {
        //多个buffer数组进行操作

        ServerSocketChannel channel = ServerSocketChannel.open();

        //设置为非阻塞型
        channel.configureBlocking(false);

        //设置两个缓冲区一起使用
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //设置ip地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);
        //绑定ip地址
        channel.bind(inetSocketAddress);

        //开始践行监听
        SocketChannel accept = channel.accept();

        //设置每次读取的最大长度
        int messageLength = 8;

        while (true) {
            //不断的读取数据
            int byteRead = 0;  //每次出现新的数据就重置

            while (byteRead < messageLength) {
                long read = accept.read(byteBuffers);  //读取的长度
                byteRead += read;   //累计计算读取的总长度

                //打印输出当前的长度
                System.out.println("byteRead长度为: " + byteRead);

                //使用流打印来打印数据
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position: "+ byteBuffer.position() + "limit " + byteBuffer.limit() ).forEach(System.out::println);

            }
            //将每个buffer进行一次反转
            Arrays.asList(byteBuffers).forEach(a -> a.flip());

            int byteWrite = 0;

            while (byteWrite < messageLength) {
                //将数据写回客户端中
                long read = accept.read(byteBuffers);
                byteWrite += read;
            }

            //清空缓冲区
            Arrays.asList(byteBuffers).forEach(a -> a.clear());

            //输出一些数据
            System.out.println("byteRead 长度为: " + byteRead + "byteWrite 长度为: " + byteWrite);

        }

    }
}
