package com.lzg.netty.keepalive;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //将evt向下转化
        IdleStateEvent event = (IdleStateEvent) evt;
        String eventType = "";

        //检测当前的状态究竟是什么
        switch (event.state()) {
            case READER_IDLE:
                eventType = "读空闲";
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
            case ALL_IDLE:
                eventType = "读写空闲";
                break;
        }
        System.out.println("当前的状态是" + eventType);

        //将当前的连接断开
        Channel channel = ctx.channel();
        channel.close();

    }
}
