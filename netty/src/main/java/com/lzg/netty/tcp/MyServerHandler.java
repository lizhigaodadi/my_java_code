package com.lzg.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    int count;   //初始化默认为0

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //每一次读取数据之后，都打印出来，并回复客户端消息
        byte[] buffer = new byte[byteBuf.readableBytes()];   //为这个数据贴身准备一个字节数组
        byteBuf.readBytes(buffer);

        //打印消息

        System.out.println("[客户端]传来消息: " + new String(buffer, StandardCharsets.UTF_8));
        System.out.println("传来消息次数 count: " + (++count));


        //会送客户端一个随机id
        ByteBuf repByteBuffer = Unpooled.copiedBuffer("id: " + UUID.randomUUID().toString(), StandardCharsets.UTF_8);
        channelHandlerContext.writeAndFlush(repByteBuffer);

    }
}
