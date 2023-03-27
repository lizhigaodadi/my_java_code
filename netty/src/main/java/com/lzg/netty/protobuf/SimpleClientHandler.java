package com.lzg.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //将protoBuf对象发送过去
        Random random = new Random();
        int i = random.nextInt(10);
        MyDataInfo.MyMessage obj = null;
        if (i%2 == 0) { //发送学生信息
            obj = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.studentType).setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("王小美").build()).build();
        } else {
            obj = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.workerType).setWorker(MyDataInfo.Worker.newBuilder().setAge(20).setName("甘雨").build()).build();
        }

        //将数据发送出去
        ctx.writeAndFlush(obj);
        System.out.println("数据成功发送出去");


    }
}
