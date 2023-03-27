package com.lzg.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //添加一个处理一个自定义的处理器
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加一个解码器
        pipeline.addLast(new ByteBufToMessageProtocolDecoder());

        pipeline.addLast(new MyServerHandler());
    }
}
