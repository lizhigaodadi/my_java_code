package com.lzg.netty.inandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //添加一个编码器
        pipeline.addLast(new MyLongToByteBufEncoder());
        //为客户端添加上一个解码器
        pipeline.addLast(new MyByteBufToLongDecoder());

        //添加一个自定义的handler
        pipeline.addLast(new MyClientHandler());
    }
}
