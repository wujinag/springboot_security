package com.example.redisnetworkmodel.reactorthread;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MutilDispatchHandler implements Runnable {

    SocketChannel channel;

    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public MutilDispatchHandler(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        processor();
    }

    private void processor() {
        executor.execute(new ReaderHandler(channel));
    }

    static class ReaderHandler implements Runnable {
        private SocketChannel channel;

        public ReaderHandler(SocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":-----");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int len = 0, total = 0;
            String msg = "";
            try {
                do {
                    len = channel.read(buffer);
                    if (len > 0) {
                        total += len;
                        msg += new String(buffer.array());
                    }
                } while (len > buffer.capacity());
                System.out.println("total:" + total);

                //msg=表示通信传输报文
                //耗时2s
                //登录： username:password
                //ServetRequets: 请求信息
                //数据库的判断
                //返回数据，通过channel写回到客户端
                System.out.println(channel.getRemoteAddress() + ": Server receive Msg:" + msg);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
