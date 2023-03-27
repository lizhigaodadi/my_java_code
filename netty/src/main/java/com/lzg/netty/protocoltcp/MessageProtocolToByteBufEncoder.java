package com.lzg.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

//自定义编码器
public class MessageProtocolToByteBufEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol, ByteBuf byteBuf) throws Exception {
        System.out.println("MessageProtocolToByteBfEncoder 的encode方法被调用了");

        //MessageProtocol 转化为 ByteBuf
        //先将数据长度的信息传递出去
        int len = messageProtocol.getLength();
        byteBuf.writeInt(len);
        byteBuf.writeBytes(messageProtocol.getContent());


    }
}
