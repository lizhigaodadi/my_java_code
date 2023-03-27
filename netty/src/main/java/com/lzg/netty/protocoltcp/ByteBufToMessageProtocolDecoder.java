package com.lzg.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import sun.plugin2.message.Message;

import java.util.List;

//自定义解码器
public class ByteBufToMessageProtocolDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //自动分配好了可读的byteBuf给我们使用
        //先获取当前这段内容的长度
        int len = byteBuf.readInt();

        //通过这个长度创建一个相应大小的byte数组
        byte[] bytes = new byte[len];
        //通过这个字节数组来读取数据
        byteBuf.readBytes(bytes);

        //将数据封装成MessageProtocol
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(len);
        messageProtocol.setContent(bytes);

        //传递给下一个处理器
        list.add(messageProtocol);

    }
}
