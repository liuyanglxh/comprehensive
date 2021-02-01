package com.liuyang.io;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO  阻塞式IO
 */
public class BIODemo {

    @Test
    public void test() throws IOException {
        ServerSocket ss = new ServerSocket(9000);
        while (true) {
            System.out.println("等待连接");
            Socket socket = ss.accept();
            System.out.println("连接进来了");
            handle(socket);
        }
    }

    private void handle(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备read");
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("read完毕");
        if (read != -1) {
            System.out.printf("接收到客户端数据 " + new String(bytes, 0, read));
        }
    }
}
