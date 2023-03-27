package com.lzg.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import sun.reflect.generics.scope.Scope;

public class MyServer {
    public static void main(String[] args) throws Exception {
        //编写一个webSocket长连接的服务器
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //配置连接的一个基本配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //装配http的编解码器
                            pipeline.addLast(new HttpServerCodec());

                            //是以块的方式来写，所以添加ChunkedWriteHandler
                            pipeline.addLast(new ChunkedWriteHandler());

                            //http发送请求的时候是以段为单位进行发送，的HttpObjectAggregator会将这些段聚合起来
                            //这也是为什么连接上http之后会出现多次请求
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /**
                             * webSocketServerProtocolHandler会将http协议升级为WebSocket协议
                             * ws://www.wangxm.top/hello  hello就是uri  这里的参数就是你要填入的uri
                             * webSocket是以帧frame为单位进行数据传输的
                             *
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            //添加自定义的handler来处理业务逻辑
                            pipeline.addLast(new MyHandler());
                        }
                    });

            //启动
            ChannelFuture cf = serverBootstrap.bind(6667).sync();
            cf.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
