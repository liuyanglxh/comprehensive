package com.liuyang.io.netty.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.jboss.netty.util.CharsetUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private static final Map<String, Channel> clients = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String message = "【" + ctx.channel().remoteAddress() + "】 加入聊天室";
        clients.put(ctx.channel().remoteAddress().toString(), ctx.channel());
        sendToClients(ctx, message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        String message = "【" + ctx.channel().remoteAddress() + "】：" + buf.toString(CharsetUtil.UTF_8);
        sendToClients(ctx, message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        clientExit(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        clientExit(ctx);
    }

    private void clientExit(ChannelHandlerContext ctx) {
        sendToClients(ctx, ctx.channel().remoteAddress() + " 退出聊天室");
        clients.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
    }

    private void sendToClients(ChannelHandlerContext ctx, String message) {
        System.out.println(message);
        clients.values().stream().filter(c -> c != ctx.channel()).forEach(channel -> {
            try {
                channel.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8)).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
