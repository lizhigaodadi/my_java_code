package com.lzg.netty.chatgroup;

import com.lzg.buffer.NioServer;
import com.lzg.netty.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.unix.Socket;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;

public class NettyChatGroupServer {
    private InetSocketAddress inetSocketAddress;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setPort(int port) {
        this.inetSocketAddress = new InetSocketAddress(port);
    }

    public void run() throws Exception {  //开启服务
        //创建两个工作循环组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .option(ChannelOption.SO_BACKLOG,128)  //设置最大连接数
                            .childOption(ChannelOption.SO_KEEPALIVE,true)   //设置连接一直保持连接的状态
                            .childHandler(new ChannelInitializer<SocketChannel>() {


                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    //添加处理字符串的handler
                                    socketChannel.pipeline().addLast(new StringDecoder());
                                    socketChannel.pipeline().addLast(new StringEncoder());
                                    socketChannel.pipeline().addLast(new NettyServerHandler());

                                }
                            });   //为workerGroup添加handler
            //开始绑定端口进行执行
            ChannelFuture cf = serverBootstrap.bind(inetSocketAddress).sync();
            System.out.println("服务器成功启动");

            //开启监听关闭端口事件
            cf.channel().closeFuture().sync();
        } finally {
            //优雅的关闭
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        NettyChatGroupServer nettyChatGroupServer = new NettyChatGroupServer();
        nettyChatGroupServer.setPort(6666);
        //启动
        nettyChatGroupServer.run();
    }
}
