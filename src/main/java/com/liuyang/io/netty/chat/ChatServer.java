package com.liuyang.io.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

public class ChatServer {

    public static void main(String[] args) {
        NioEventLoopGroup main = new NioEventLoopGroup(1, new DefaultThreadFactory("main"));
        NioEventLoopGroup sub = new NioEventLoopGroup(1, new DefaultThreadFactory("sub"));

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(main, sub)
                    .channel(NioServerSocketChannel.class)
                    /*
                     *  配置好各种参数，可以大幅提升性能
                     */
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("netty server start");
            ChannelFuture cf = bootstrap.bind(9000).sync();
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            main.shutdownGracefully();
            sub.shutdownGracefully();
        }
    }
}
