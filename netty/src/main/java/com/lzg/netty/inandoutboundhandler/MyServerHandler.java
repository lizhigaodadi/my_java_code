package com.lzg.netty.inandoutboundhandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long msg) throws Exception {
        //将收到的消息打印出来
        Channel channel = channelHandlerContext.channel();
        System.out.println("收到[客户端]" + channel.remoteAddress() + "发来的消息 long : " + msg);

        //回个消息
        channelHandlerContext.writeAndFlush(98765L);

    }
}
