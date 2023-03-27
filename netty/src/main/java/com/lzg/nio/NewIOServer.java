package com.lzg.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) {
        ServerSocketChannel ssc = null;
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);
        FileOutputStream fos = null;

        try {
            ssc = ServerSocketChannel.open();
            ssc.bind(inetSocketAddress);  //绑定ip

            //开始监听
            while (true) {
                SocketChannel channel = ssc.accept();
                //读取数据
                ByteBuffer byteBuffer = ByteBuffer.allocate(4024);
//                int read = 0;
//                while (read != -1) {
                channel.read(byteBuffer);
                byteBuffer.rewind();   //倒带，将position和mark重回初始状态
//                }

                fos = new FileOutputStream("3.txt");
                fos.write(byteBuffer.array());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
