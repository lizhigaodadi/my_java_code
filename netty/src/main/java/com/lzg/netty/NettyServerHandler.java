package com.lzg.netty;

import com.lzg.netty.websocket.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //打印出传输过来对象的信息
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("student.id: " + student.getId() + " student.name: " + student.getName());

    }


    //在读写完成之后执行
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //反馈给客户端一条信息,直接使用封装好了的api
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端",StandardCharsets.UTF_8));
    }


    //当有异常抛出的时候执行，关闭相关的东西
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();  //直接关闭
    }
}
