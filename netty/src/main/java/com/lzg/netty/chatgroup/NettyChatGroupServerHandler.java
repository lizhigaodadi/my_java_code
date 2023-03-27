package com.lzg.netty.chatgroup;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyChatGroupServerHandler extends SimpleChannelInboundHandler<String> {
    //创建这玩意需要一个全局事件执行器，这是一个单例的
    private ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override   //这个是钩子方法中最先执行的，所以在这里将channel加入到group中去
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取Channel
        Channel channel = ctx.channel();
        //加入到group中去
        group.add(channel);
        System.out.println("[客户端]" + channel.remoteAddress() + "加入聊天");

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //有客户端上线了，我们要告知其他的人
        Channel channel = ctx.channel();
        group.writeAndFlush(channel.remoteAddress()+" 已上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //告知所有人
        group.writeAndFlush(channel.remoteAddress() + "已下线");
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //又客户端脱离了连接告知其他人
        System.out.println(ctx.channel().remoteAddress() + "离线");
        //打印
        System.out.println("group size" + group.size());


    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel currentChannel = channelHandlerContext.channel();
        group.forEach((channel)-> {
            if (currentChannel == channel) {
                //发现是通过一个
                channel.write("[自己]发送了:" + s);
            } else {
                channel.write("[其他人]"+ channel.remoteAddress() + "发送了: "+s);
            }

        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();   //关闭
    }
}
