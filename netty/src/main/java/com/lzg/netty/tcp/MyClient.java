package com.lzg.netty.tcp;

import com.lzg.netty.inandoutboundhandler.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new MyClientInitializer());

            //启动连接
            ChannelFuture cf = bootstrap.connect("localhost", 6666).sync();
            cf.channel().closeFuture().sync();
            System.out.println(cf.channel().remoteAddress());
        } finally {
            group.shutdownGracefully();
        }


    }
}
