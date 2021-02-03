package com.liuyang.io.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.jboss.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * OP_READ:1
 * OP_WRITE:4
 * OP_CONNECT:8
 * OP_ACCEPT:16
 */
public class ChatClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ChatClientHandler());
                        }
                    });
            System.out.println("netty client start");
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 9000).sync();
            new Thread(() -> {
                while (true) {
                    Scanner sc = new Scanner(System.in);
                    while (sc.hasNext()) {
                        String str = sc.next();
                        try {
                            ByteBuf buf = Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
                            ChannelFuture f = cf.channel().writeAndFlush(buf).sync();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }).start();
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
}
