package com.lzg.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ChannelTest04 {
    public static void main(String[] args) throws Exception {
        //输入输出流
        FileInputStream fis = new FileInputStream("f://34.jpg");
        FileOutputStream fos = new FileOutputStream("f://copy34.jpg");

        //通道
        FileChannel src_fic = fis.getChannel();
        FileChannel dest_foc = fos.getChannel();

        //直接通过内部的api进行复制
        dest_foc.transferFrom(src_fic,0,src_fic.size());

        //关闭流和通道
        src_fic.close();
        dest_foc.close();
        fis.close();
        fos.close();
    }
}
