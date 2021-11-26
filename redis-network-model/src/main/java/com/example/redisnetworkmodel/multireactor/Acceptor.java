package com.example.redisnetworkmodel.multireactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Acceptor implements Runnable {

    final Selector sel;

    final ServerSocketChannel serverSocketChannel;

    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private Executor subReactorExecutor = Executors.newFixedThreadPool(POOL_SIZE);

    private Reactor[] subReactors = new Reactor[POOL_SIZE];

    int handerNext = 0;

    public Acceptor(Selector sel, int port) throws IOException {
        this.sel = sel;
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // 设置成非阻塞模式
        this.serverSocketChannel.configureBlocking(false);
        // 注册到 选择器 并设置处理 socket 连接事件
        this.serverSocketChannel.register(this.sel, SelectionKey.OP_ACCEPT, this);
        init();
        System.out.println("Main Reactor Acceptor: Listening on port:" + port);
    }

    private void init() throws IOException {
        for (int i = 0; i < subReactors.length; i++) {
            subReactors[i] = new Reactor();
            subReactorExecutor.execute(subReactors[i]);
        }
    }

    @Override
    public void run() {
        //负责处理连接事件和IO事件
        try {
            // 接收连接，非阻塞模式下，没有连接直接返回 null
            SocketChannel socketChannel = serverSocketChannel.accept(); //获取连接
            if (socketChannel != null) {
                // 把提示发到界面
                socketChannel.write(ByteBuffer.wrap("Multiply Reactor Patterm\r\nreactor> ".getBytes()));
                System.out.println(Thread.currentThread().getName() + ": Main-Reactor-Acceptor:" + socketChannel.getLocalAddress() + "连接");
                // 如何解决呢，直接调用 wakeup，有可能还没有注册成功又阻塞了。这是一个多线程同步的问题，可以借助队列进行处理
                Reactor subReactor = subReactors[handerNext];
                subReactor.register(new AsyncHandler(socketChannel));
                //轮询
                if (++handerNext == subReactors.length) {
                    handerNext = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
