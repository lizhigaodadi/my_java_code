package com.lzg.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

//客户端和服务器端相互通讯的数据被封装成 HttpObject
public class MyTestServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        //首先判断是否是httpRequest请求
        if (httpObject instanceof HttpRequest) {

            ByteBuf byteBuf = Unpooled.copiedBuffer("fuck you",StandardCharsets.UTF_8);

            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,byteBuf);


            //打印相关信息
            System.out.println("连接客户端: " + channelHandlerContext.channel().remoteAddress());
            System.out.println("通道hashCode: " + channelHandlerContext.pipeline().hashCode());

            //过滤掉请求图标的信息
            HttpRequest httpRequest = (HttpRequest) httpObject;
            //生成URI对象
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("客户端请求了favicon.ico，服务器不做回应");
                return;
            }

            //设置请求头信息
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

            channelHandlerContext.writeAndFlush(httpResponse);
        }
    }
}
