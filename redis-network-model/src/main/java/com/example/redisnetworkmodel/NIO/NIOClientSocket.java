package com.example.redisnetworkmodel.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClientSocket {

    public static void main(String[] args) {
        try {
            //连接
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8080));
            if (socketChannel.isConnectionPending()) {
                socketChannel.finishConnect();
            }
            //发送数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("Hello I'M SocketChannel Client".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            //读取服务端数据
            byteBuffer.clear();
            while (true) {
                int i = socketChannel.read(byteBuffer);
                if (i > 0) {
                    System.out.println("收到服务端的数据：" + new String(byteBuffer.array()));
                } else {
                    System.out.println("服务端数据未准备好");
                    Thread.sleep(1000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
