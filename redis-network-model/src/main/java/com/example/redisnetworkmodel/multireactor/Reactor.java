package com.example.redisnetworkmodel.multireactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Reactor implements Runnable {

    private final Selector selector;

    private ConcurrentLinkedQueue<AsyncHandler> events = new ConcurrentLinkedQueue<>();

    public Reactor() throws IOException {
        this.selector = Selector.open();
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            AsyncHandler handler;
            try {
                while ((handler = events.poll()) != null) {
                    handler.getChannel().configureBlocking(false);
                    SelectionKey selectionKey = handler.getChannel().register(selector, SelectionKey.OP_READ);
                    selectionKey.attach(handler);
                    handler.setSk(selectionKey);
                }
                selector.select(); //阻塞
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    Runnable runnable = (Runnable) key.attachment(); //得到Acceptor实例
                    if (runnable != null) {
                        runnable.run();
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void register(AsyncHandler handler) {
        events.offer(handler); //有一个事件注册
        selector.wakeup();
    }
}
