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
        this.serverSocketChannel.configureBlocking(false);
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
            SocketChannel socketChannel = serverSocketChannel.accept(); //获取连接
            if (socketChannel != null) {
                socketChannel.write(ByteBuffer.wrap("Multiply Reactor Patterm\r\nreactor> ".getBytes()));
                System.out.println(Thread.currentThread().getName() + ": Main-Reactor-Acceptor:" + socketChannel.getLocalAddress() + "连接");
                Reactor subReactor = subReactors[handerNext];
                subReactor.register(new AsyncHandler(socketChannel));
                if (++handerNext == subReactors.length) {
                    handerNext = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
