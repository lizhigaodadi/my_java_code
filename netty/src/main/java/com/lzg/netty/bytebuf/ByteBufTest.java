package com.lzg.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class ByteBufTest {
    public static void main(String[] args) {
        //创建一个大小为10的byteBuf
        //Unpooled是一个操作ByteBuf德工具类
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        //读取数据
        for (int i = 0; i < 10; i++) {
            System.out.println(buffer.getByte(i));
        }

        System.out.println(buffer.readByte());


        //直接通过数据创建一个ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world! fuck you", StandardCharsets.UTF_8);

        System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
        System.out.println(byteBuf.getCharSequence(5, 6, StandardCharsets.UTF_8));
    }






}
