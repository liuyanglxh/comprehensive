package com.liuyang.io.netty.split;

import com.liuyang.io.netty.pojo.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ServerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //先查看可读取的数据是否有4个字节
        int i = in.readableBytes();
        if (i < 4) {
            System.out.println("不足4个，等待");
            return;
        }
        int length = in.readInt();
        if (in.readableBytes() < length) {
            System.out.println("数据没有发送完");
            return;
        }
        byte[] data = new byte[length];
        in.readBytes(data);
        out.add(new Protocol(data));
    }
}
