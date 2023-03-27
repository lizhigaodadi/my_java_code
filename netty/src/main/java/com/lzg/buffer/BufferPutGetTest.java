package com.lzg.buffer;

import java.nio.ByteBuffer;

public class BufferPutGetTest {
    //测试buffer的putget方法
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putInt(10);
        buffer.putChar('i');
        buffer.putLong(222L);
        buffer.putShort((short) 12);

        //反转下标
        buffer.flip();

        //必须按照原来的顺序读取数据，因为每个类型所占的大小不同，如果乱读取的话，会导致下标乱来
        System.out.println(buffer.getInt());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
    }
}
