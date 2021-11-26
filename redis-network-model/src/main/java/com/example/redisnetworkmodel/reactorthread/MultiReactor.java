package com.example.redisnetworkmodel.reactorthread;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiReactor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    //建立连接，同时注册到selector中
    public MultiReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new MultiAcceptor(selector, serverSocketChannel));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                //遍历目前队列中的操作
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    //分发
                    dispatch(iterator.next());
                    //遍历之后移除
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey key) {
        //可能拿到的对象有两个
        // Acceptor
        // Handler
        Runnable runnable = (Runnable) key.attachment();
        if (runnable != null) {
            runnable.run(); //
        }
    }
}
