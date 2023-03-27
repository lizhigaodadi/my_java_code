package com.lzg.netty.protobuf;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage myMessage) throws Exception {
        //先来判断类型
        if (myMessage.getDataType().equals(MyDataInfo.MyMessage.DataType.studentType)) {
            System.out.println("student id: " + myMessage.getStudent().getId() + "student name: " + myMessage.getStudent().getName());
        } else if (myMessage.getDataType().equals(MyDataInfo.MyMessage.DataType.workerType)) {
            System.out.println("worker age: " + myMessage.getWorker().getAge() + "worker name: " + myMessage.getWorker().getName());
        } else {
            System.out.println("发送过来的数据格式不正确");
        }
    }
}
