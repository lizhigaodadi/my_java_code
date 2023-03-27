package com.lzg.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ReadOnlyBufferTest {
    public static void main(String[] args) {
        //测试将一个buffer转化为只读
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        for (int i=0;i<30;i++) {
            buffer.put((byte) i );
        }
        buffer.flip();

        //将buffer转化为只读的
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        //读取数据
        while (readOnlyBuffer.hasRemaining()) { //hasRemaining是通过position和limit的位置来进行判断的
            System.out.println(readOnlyBuffer.get());
        }


        //写入数据
//        readOnlyBuffer.put("hello".getBytes(StandardCharsets.UTF_8));




    }
}
