package com.lzg.netty.inandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        long message = 123456L;
        //将一个long数据发送给服务器
        System.out.println("client send a Long Message " + message);
        //将数据发送出去
        ctx.writeAndFlush(message);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将msg强制类型转换
        long l = (long) msg;
        System.out.println("[服务器]发来消息: " + l);
    }
}
