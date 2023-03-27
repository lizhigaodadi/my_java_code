package com.lzg.netty.chatgroup;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyChatGroupClientHandler extends SimpleChannelInboundHandler<String> {
    //因为传递过来的数据是String类型的，所以我们这里直接设置为接收String类型的handler
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//        channelHandlerContext.writeAndFlush(s);
        System.out.println(s.trim());
    }
}
