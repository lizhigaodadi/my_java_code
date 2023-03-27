package com.lzg.netty;

import com.lzg.netty.websocket.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.net.InetSocketAddress;

public class NettySimpleServer {
    //netty服务端
    public static void main(String[] args) throws Exception {
        //创建两个组
        //参数为cpu内核数量，默认是2个
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);  //用于处理连接请求
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); //用于处理读写请求

        //通过ServerBootstrap来启动服务器

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //通过链式编程来设置相应的参数
        serverBootstrap.group(bossGroup,workerGroup)   //设置两个线程组
                        .channel(NioServerSocketChannel.class)   //设置所使用的通道的实例
//                        .option(ChannelOption.SO_BACKLOG,128)  //设置线程队列得到的连接个数
//                        .childOption(ChannelOption.SO_KEEPALIVE,true)   //设置一直保持在活跃的状态
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                //向管道中添加相应的编码器
                                socketChannel.pipeline().addLast("decoder",new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));

                                //获取管道，再通过管道添加handler
                                socketChannel.pipeline().addLast(new NettyServerHandler());
                            }
                        });
        //启动服务器并绑定端口
        ChannelFuture cf = serverBootstrap.bind(new InetSocketAddress("127.0.0.1", 7000));//绑定端口号
        System.out.println("server is ready...");

        //设置cf异步操作结果出来之后的回调操作
        cf.addListener(channelFuture -> {
            if (channelFuture.isSuccess()) {
                System.out.println("端口监听成功");
            } else {
                System.out.println("端口监听失败");
            }
        });

        //开启对关闭通道行为的监听
        cf.channel().closeFuture().sync();


    }
}
