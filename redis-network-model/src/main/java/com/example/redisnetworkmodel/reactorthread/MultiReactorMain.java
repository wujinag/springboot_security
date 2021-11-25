package com.example.redisnetworkmodel.reactorthread;


import java.io.IOException;

public class MultiReactorMain {
    public static void main(String[] args) throws IOException {
        new Thread(new MultiReactor(8080), "Main-Thread").start();
    }
}
