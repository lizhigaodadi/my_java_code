package com.lzg.netty.chatgroup;

import com.lzg.netty.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NettyChatGroupClient {
    private String host;
    private int port;

    public void setHostAndPort(String host,int port) {
        this.host = host;
        this.port = port;
    }


    public void run() throws Exception {
        //连接至服务器
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //添加对字符串编解码的处理器
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());

                    //添加自定义的handler
                    pipeline.addLast(new NettyChatGroupClientHandler());
                }
            });
            //异步连接服务器
            System.out.println("hi~");
            ChannelFuture cf = bootstrap.connect(host, port).sync();

            System.out.println("hi");
            //异步监听关闭事件
            cf.channel().closeFuture().sync();

            Channel channel = cf.channel();

            System.out.println("成功连接上服务器" + channel.remoteAddress());


            //发送信息给服务器
            Scanner scanner = new Scanner(System.in);
            String message = "";
            while (scanner.hasNextLine()) {
                message = scanner.nextLine();

                //将消息发送出去
                channel.writeAndFlush(message + "\n");
            }
        } finally {
            //优雅的关闭事件循环组
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        NettyChatGroupClient nettyChatGroupClient = new NettyChatGroupClient();
        nettyChatGroupClient.setHostAndPort("127.0.0.1",6666);
        //开始连接
        nettyChatGroupClient.run();
    }
}
