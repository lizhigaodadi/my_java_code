package com.lzg.netty;

import com.lzg.netty.websocket.StudentPOJO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.net.SocketImpl;

public class NettySimpleClient {
    //netty客户端
    public static void main(String[] args) throws Exception {
        //客户端只需要一个事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            //客户端通过Bootstrap来启动
            Bootstrap bootstrap = new Bootstrap();

            //链式编程设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("encoder",new ProtobufEncoder());
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("client is ready...");

            //进行连接
            ChannelFuture cf = bootstrap.connect("127.0.0.1",7000).sync();


            //监听关闭事件
            cf.channel().closeFuture().sync();

        } finally {
            //优雅的关闭工作组
            group.shutdownGracefully();
        }
    }
}
