package com.lzg.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        //测试使用MappedByteBuffer
        //因为是直接在内存中操作的，所以效率十分的高
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw"); //可读可写

        //获取通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 设置模式为可读可写，读写的起始位置为0，可以操作内存大小为5，所以5这个下标位置是操作不了的
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0,(byte) 'J');
        map.put(3,(byte) 'C' );

        //结束关闭相关操作
        randomAccessFile.close();
        System.out.println("操作结束");


    }
}
