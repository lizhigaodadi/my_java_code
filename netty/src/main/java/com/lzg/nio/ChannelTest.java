package com.lzg.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

//测试Channel的强大
public class ChannelTest {
    public static void main(String[] args) throws IOException {
        String hello = "hello world";

        FileOutputStream os = new FileOutputStream("f://111.txt");

        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //通过输出流来获取一个文件通道
        FileChannel fileChannel = os.getChannel();

        //缓冲区读取数据
        buffer.put(hello.getBytes(StandardCharsets.UTF_8));

        //反转
        buffer.flip();

        //将缓冲区关联通道,站在通道的角度吸入数据
        fileChannel.write(buffer);  //此时输出流的工作已经结束，直接关闭即可

        os.close();

    }
}
