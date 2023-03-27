package com.lzg.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //添加一个处理一个自定义的处理器
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new MyServerHandler());
    }
}
