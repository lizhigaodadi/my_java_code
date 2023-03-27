package com.lzg.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    int count;   //初始化默认为0


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        byte[] content = messageProtocol.getContent();

        System.out.println("[客户端]发来消息: " + new String(content, StandardCharsets.UTF_8));
        System.out.println("消息发送次数 count: " + (++count));
    }
}
