package com.lzg.buffer;

import java.nio.IntBuffer;

//测试buffer缓冲区
public class BufferTest {
    public static void main(String[] args) {
        IntBuffer intBuffer =IntBuffer.allocate(5);   //创建一个大小为5的buffer

        for (int i=0;i<5;i++) {
            intBuffer.put(10+i);
        }


        //反转取出数据
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {  //如果buffer里面还有数据就不会停下来
            System.out.println(intBuffer.get());
        }
    }
}
