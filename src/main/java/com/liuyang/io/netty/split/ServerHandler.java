package com.liuyang.io.netty.split;

import com.liuyang.io.netty.pojo.Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Protocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Protocol msg) throws Exception {
        System.out.println("服务端接收到数据");
        System.out.println(new String(msg.getData()));
    }
}
