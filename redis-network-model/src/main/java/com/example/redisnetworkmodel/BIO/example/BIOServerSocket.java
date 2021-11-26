package com.example.redisnetworkmodel.BIO.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServerSocket {

    //先定义一个端口号，这个端口的值是可以自己调整的。
    static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws IOException {
        //在服务器端，我们需要使用ServerSocket，所以我们先声明一个ServerSocket变量
        ServerSocket serverSocket = null;
        //接下来，我们需要绑定监听端口, 那我们怎么做呢？只需要创建使用serverSocket实例
        //ServerSocket有很多构造重载，在这里，我们把前边定义的端口传入，表示当前ServerSocket监听的端口是8080
        serverSocket = new ServerSocket(DEFAULT_PORT);
        System.out.println("启动服务，监听端口：" + DEFAULT_PORT);

        //获取客户端请求
        //我们要使用的是accept这个函数，当accept方法获得一个客户端请求时
        //会返回一个socket对象， 这个socket对象让服务器可以用来和客户端通信的一个端点。

        //开始等待客户端连接，如果没有客户端连接，就会一直阻塞在这个位置
        Socket socket = serverSocket.accept();
        //很可能有多个客户端来发起连接，为了区分客户端，咱们可以输出客户端的端口号
        System.out.println("客户端：" + socket.getPort() + "已连接");
        //一旦有客户端连接过来，我们就可以用到IO来获得客户端传过来的数据。
        //使用InputStream来获得客户端的输入数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String clientStr = bufferedReader.readLine(); //读取一行信息
        System.out.println("客户端发了一段消息：" + clientStr);

        //服务端收到数据以后，可以给到客户端一个回复。
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("我已经收到你的消息了\n");
        bufferedWriter.flush(); //清空缓冲区触发消息发送
    }
}