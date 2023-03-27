package com.lzg.netty.inandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public class MyByteBufToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteBufToLongDecoder decode 执行了");

        if (byteBuf.readableBytes() >= 8) {   //一个long类型占据了8个字节
            list.add(byteBuf.readLong());   //将读取出来的数据传入list，然后由list转交给下一个handler
        }
    }
}
