package com.lzg.netty.inandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //增加一个byteBuf转long的解码器
        pipeline.addLast(new MyByteBufToLongDecoder());
        //添加一个编码器
        pipeline.addLast(new MyLongToByteBufEncoder());

        //添加一个自定义的处理器
        pipeline.addLast(new MyServerHandler());


    }
}
