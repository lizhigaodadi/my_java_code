package com.lzg.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDate;

public class MyHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded被调用了"+ctx.channel().id().asLongText());
        System.out.println("handlerAdded被调用了"+ctx.channel().id().asShortText());

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用了" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印错误信息
        System.out.println(cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //将信息打印出来
        Channel channel = channelHandlerContext.channel();
        System.out.println(channel.remoteAddress() +" : " + textWebSocketFrame.text());

        channel.writeAndFlush("服务器时间:" + LocalDate.now() + textWebSocketFrame.text());
    }


}
