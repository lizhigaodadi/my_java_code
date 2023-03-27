package com.lzg.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sun.plugin2.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String content = "天气好冷，我要吃火锅";
        //重复发消息给服务器
        String message;
        for (int i = 0; i < 10; i++) {
            message = content + i;
            //将message封装为MessageProtocol发送出去
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            int len = bytes.length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(bytes);
            messageProtocol.setLength(len);
            //发送出去
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //统计一共发送了多少消息过来
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        System.out.println("[服务器]发送过来消息: " + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("消息发送次数 count: " + (++count));
    }
}
