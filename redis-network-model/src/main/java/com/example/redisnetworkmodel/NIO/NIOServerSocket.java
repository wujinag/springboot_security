package com.example.redisnetworkmodel.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOServerSocket {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); //设置非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            //保持连接
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    //读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    socketChannel.read(buffer);
                    System.out.println(new String(buffer.array()));

                    //写出数据
                    Thread.sleep(10000); //阻塞一段时间
                    //当数据读取到缓冲区之后，接下来就需要把缓冲区的数据写出到通道，而在写出之前必须要调用flip方法，实际上就是重置一个有效字节范围，然后把这个数据接触到通道。
                    buffer.flip();
                    socketChannel.write(buffer);//写出数据
                } else {
                    Thread.sleep(1000);
                    System.out.println("连接未就绪");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
