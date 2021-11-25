package com.example.redisnetworkmodel.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOServerSocket {
    //NIO中的核心
    //channel
    //buffer
    //selector

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); //设置连接非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            while (true) {
                //是非阻塞的
                SocketChannel socketChannel = serverSocketChannel.accept(); //获得一个客户端连接
//                socketChannel.configureBlocking(false);//IO非阻塞
                if (socketChannel != null) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int i = socketChannel.read(byteBuffer);
                    Thread.sleep(10000);
                    byteBuffer.flip(); //反转
                    socketChannel.write(byteBuffer);
                } else {
                    Thread.sleep(1000);
                    System.out.println("连接位就绪");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
