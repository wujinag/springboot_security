package com.example.redisnetworkmodel.BIO.update;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServerSocket {

    //先定义一个端口号，这个端口的值是可以自己调整的。
    static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(DEFAULT_PORT);
        System.out.println("启动服务，监听端口：" + DEFAULT_PORT);

        while (true) { //case1: 增加循环，允许循环接收请求
            Socket socket = serverSocket.accept();
            System.out.println("客户端：" + socket.getPort() + "已连接");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientStr = bufferedReader.readLine(); //读取一行信息
            System.out.println("客户端发了一段消息：" + clientStr);
            Thread.sleep(20000); //case2: 修改：增加等待时间
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("我已经收到你的消息了\n");
            bufferedWriter.flush(); //清空缓冲区触发消息发送
        }
    }
}