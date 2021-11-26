package com.example.redisnetworkmodel.reactorthread;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MultiAcceptor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    public MultiAcceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        SocketChannel channel;

        try {
            channel = serverSocketChannel.accept();//得到一个客户端连接
            System.out.println(channel.getRemoteAddress() + ":收到一个客户端连接");
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ, new MultiDispatchHandler(channel));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
