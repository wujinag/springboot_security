package com.example.redisnetworkmodel.multireactor;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class AsyncHandler implements Runnable {

    private SocketChannel channel;

    private SelectionKey sk;
    //存储客户端的完整消息
    StringBuilder stringBuilder = new StringBuilder();

    ByteBuffer inputBuffer = ByteBuffer.allocate(1024);

    ByteBuffer outputBuffer = ByteBuffer.allocate(1024);

    public AsyncHandler(SocketChannel channel) {
        this.channel = channel;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public SelectionKey getSk() {
        return sk;
    }

    public void setSk(SelectionKey sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        try {
            if (sk.isReadable()) {
                read();
            } else if (sk.isWritable()) {
                write();
            }
        } catch (Exception e) {
        }
    }

    private void read() throws IOException {
        inputBuffer.clear();
        int n = channel.read(inputBuffer);
        if (inputBufferComplete(n)) {
            System.out.println(Thread.currentThread().getName() + ": Server端收到客户端的请求消息：" + stringBuilder.toString());
            outputBuffer.put(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            this.sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private boolean inputBufferComplete(int bytes) throws EOFException {
        if (bytes > 0) {
            inputBuffer.flip(); //转化成读取模式
            while (inputBuffer.hasRemaining()) { //判断缓冲区中是否还有元素
                byte ch = inputBuffer.get(); //得到输入的字符
                if (ch == 3) { //表示Ctrl+c
                    throw new EOFException();
                } else if (ch == '\r' || ch == '\n') { //表示换行符
                    return true;
                } else {
                    stringBuilder.append((char) ch); //拼接读取到的数据
                }
            }
        } else if (bytes == 1) {
            throw new EOFException(); //客户端关闭了连接
        }
        return false;
    }

    private void write() throws IOException {
        int write = -1;
        outputBuffer.flip();
        if (outputBuffer.hasRemaining()) {
            write = channel.write(outputBuffer); //把收到的数据写回到客户端
        }
        outputBuffer.clear();
        stringBuilder.delete(0, stringBuilder.length());
        if (write <= 0) { //表示客户端没有输信息
            this.sk.channel().close();
        } else {
            channel.write(ByteBuffer.wrap("\r\nreactor> ".getBytes()));
            this.sk.interestOps(SelectionKey.OP_READ);//又转化为读事件
        }
    }
}
