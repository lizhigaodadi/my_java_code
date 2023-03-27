package com.lzg.netty.http;

import com.sun.net.httpserver.HttpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //添加http服务的编解码处理器
        socketChannel.pipeline().addLast("MyHttpServerCodec",new HttpServerCodec());

        //添加自定义的处理器
        socketChannel.pipeline().addLast("MyTestServerHandler",new MyTestServerHandler());
    }
}
