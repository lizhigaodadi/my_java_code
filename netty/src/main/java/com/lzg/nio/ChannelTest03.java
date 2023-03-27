package com.lzg.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//实现文件的复制
public class ChannelTest03 {
    public static void main(String[] args) throws Exception {
        //输入流
        File file = new File("1.txt");
        FileInputStream fis = new FileInputStream(file);
        //输出流
        FileOutputStream fos = new FileOutputStream("2.txt");

        //获取通道
        FileChannel fileInputChannel = fis.getChannel();
        FileChannel fileOutputChannel = fos.getChannel();

        //根据文件实际大小创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);  //long转int

//        //读取
//        fileInputChannel.read(byteBuffer);
//        //反转
//        byteBuffer.flip();
//        fileOutputChannel.write(byteBuffer);
        //通过while循环来进行读取
        int length;
        while (((length = fileInputChannel.read(byteBuffer)) != -1)){
            //反转
            byteBuffer.flip();
            //读取数据
            fileOutputChannel.write(byteBuffer);

            //清空buffer
            byteBuffer.clear();
        }

        //关闭流
        fis.close();
        fos.close();
    }
}
