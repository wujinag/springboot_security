package com.example.redisnetworkmodel.Reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable{
    SocketChannel channe;

    public Handler(SocketChannel channe) {
        this.channe = channe;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"------");
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        /*try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        int len=0,total=0;
        String msg="";
        try {
            do {
                len = channe.read(buffer);
                if(len>0){
                    total+=len;
                    msg+=new String(buffer.array());
                }
            } while (len > buffer.capacity());
            System.out.println("total:"+total);

            //msg=表示通信传输报文
                //耗时2s
               //登录： username:password
               //ServetRequets: 请求信息
              //数据库的判断
            //返回数据，通过channel写回到客户端

            System.out.println(channe.getRemoteAddress()+": Server receive Msg:"+msg);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(channe!=null){
                try {
                    channe.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
