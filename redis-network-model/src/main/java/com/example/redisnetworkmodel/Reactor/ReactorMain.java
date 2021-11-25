package com.example.redisnetworkmodel.Reactor;

import java.io.IOException;

public class ReactorMain {
    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8080), "Main-Thread").start();
    }
}
