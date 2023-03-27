package com.lzg.netty;

import com.lzg.netty.websocket.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将数据展现出来
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器 " + ctx.channel().remoteAddress() + "发送来消息: " + byteBuf.toString(StandardCharsets.UTF_8));

        //当前线程为
        System.out.println("当前线程为: " + Thread.currentThread().getName());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端成功连接上服务器: " + ctx.channel().remoteAddress());
        //发送消息过去
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，这里是客户端~".getBytes(StandardCharsets.UTF_8)));

        //将ProtoBuf创建出来的对象通过网络传输出去
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("亚当重锤").build();
        //讲这个数据直接传输出去
        ctx.writeAndFlush(student);
        System.out.println("成功发送完消息");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
