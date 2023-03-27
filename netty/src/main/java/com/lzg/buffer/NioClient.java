package com.lzg.buffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {
    public static void main(String[] args) throws Exception {
        //创建一个SocketChannel来进行连接
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞和端口号
        socketChannel.configureBlocking(false);
//        socketChannel.bind(new InetSocketAddress("127.0.0.1",6666));
        socketChannel.connect(new InetSocketAddress("127.0.0.1",6666));

        //开始进行连接
       if (!socketChannel.isConnected()) {
           //这时还没有成功连接上服务器我们可以做一些其他的事情
           while (!socketChannel.finishConnect()) {
               System.out.println("客户端还没有成功连接上服务器,我们可以做一些其他的事情");
           }
       }
       //开始做正事
        String message = "hello World ~";
       //根据数据的大小来创建相应的buffer
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
        //通道读取buffer中的数据
        socketChannel.write(buffer);
        System.out.println("成功发送完消息");
        System.in.read();  //卡在这里
    }
}
