package com.example.redisnetworkmodel.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用
 */
public class NIOSelectorServerSocket implements Runnable {

    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public NIOSelectorServerSocket(int port) throws IOException {
        selector = Selector.open();  //多路复用器
        serverSocketChannel = ServerSocketChannel.open();
        //如果采用selector模型，必须要设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        //针对serverSocketChannel注册一个ACCEPT连接监听事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select(); //阻塞等待事件就绪
                Set selected = selector.selectedKeys(); //事件列表
                Iterator it = selected.iterator();
                while (it.hasNext()) {
                    //说明有连接进来
                    dispatch((SelectionKey) it.next());//分发事件
                    it.remove();//移除当前就绪的事件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey key) throws IOException {
        if (key.isAcceptable()) { //如果是客户端的连接事件，则需要针对该连接注册读写事件
            register(key);
        } else if (key.isReadable()) { //读事件
            read(key);
        } else if (key.isWritable()) { //写事件
            write(key);
        }
    }

    private void register(SelectionKey key) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel(); //客户端连接
        SocketChannel socketChannel = channel.accept(); //获得客户端连接
        socketChannel.configureBlocking(false);
        //把当前客户端连接注册到selector上，注册事件为READ，
        // 也就是当前channel可读时，就会触发事件，然后读取客户端的数据
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        //得到的是socketChannel
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer); //把数据从channel读取到缓冲区
        System.out.println("Server Receive Msg:" + new String(byteBuffer.array()));
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //写一个信息给到客户端
        channel.write(ByteBuffer.wrap("hello Client,I'm NIO Server\r\n".getBytes()));
    }

    public static void main(String[] args) throws IOException {
        NIOSelectorServerSocket selectorServerSocket = new NIOSelectorServerSocket(8080);
        new Thread(selectorServerSocket).start();
    }
}
