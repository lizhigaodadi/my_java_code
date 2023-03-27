package com.lzg.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class ChannelTest02 {
    public static void main(String[] args) throws Exception {
        //读取文件中的信息
        //创建输入
        FileInputStream fileInputStream = new FileInputStream("f://111.txt");

        //获取通道
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(512);
        //buffer.flip();   //反转

        //向缓冲区中写入数据
        fileChannel.read(buffer);

        //将缓冲区中的数据转化为字符串
        System.out.println(new String(buffer.array()));  //转化为字节数组，再转化为字符串
        //这里是直接使用了底层维护的数组，而没有使用到position，所以可以不用flip

        fileInputStream.close();

    }
}
