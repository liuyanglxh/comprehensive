package com.liuyang.io.netty.split;

import com.liuyang.io.netty.pojo.Protocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class ClientHandler extends SimpleChannelInboundHandler<Protocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //client启动就发送100条消息，并且把消息封装进protocol中
        Channel channel = ctx.channel();
        for (int i = 0; i < 100; i++) {
            String msg = "我是message " + i;
            channel.writeAndFlush(new Protocol(msg.getBytes(StandardCharsets.UTF_8)));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Protocol msg) throws Exception {

    }
}
