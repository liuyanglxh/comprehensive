package com.liuyang.io.netty.split;

import com.liuyang.io.netty.pojo.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientEncoder extends MessageToByteEncoder<Protocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        //写入ByteBuf：先写入数据长度，然后写入数据本身
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getData());
    }
}
