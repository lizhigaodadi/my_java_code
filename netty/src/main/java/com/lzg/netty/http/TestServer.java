package com.lzg.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

//使用netty来实现http服务
public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        //开启两个事件循环组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup,workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new TestServerInitializer());

        //异步启动绑定端口
        ChannelFuture cf = serverBootstrap.bind(8081).sync();

        //监听关闭连接的行为
        cf.channel().closeFuture().sync();
    }
}
