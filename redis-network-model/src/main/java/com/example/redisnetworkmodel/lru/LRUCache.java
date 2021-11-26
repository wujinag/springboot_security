package com.example.redisnetworkmodel.lru;

import java.util.HashMap;

public class LRUCache {

    private Node head;

    private Node tail;

    private final HashMap<String, Node> nodeHashMap;

    private int capacity; //容量

    //初始化
    public LRUCache(int capacity) {
        this.capacity = capacity;
        nodeHashMap = new HashMap<>();
        tail = new Node();
        head = new Node();
        head.next = tail;
        tail.prev = head;
    }

    //移除节点
    private void removeNode(Node node) {
        //判断是否为头节点还是为节点
        if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else if (node == head) {
            head = head.next;
            head.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    //添加节点到头部
    private void addNodeToHead(Node node) {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
    }

    //将节点移动至头节点
    private void moveNodeToHead(Node node) {
        removeNode(node);
        addNodeToHead(node);
    }

    //获取节点
    public String get(String key) {
        //从map获取，o1
        Node node = nodeHashMap.get(key);
        if (node == null) {
            return null;
        }
        //刷新当前key的位置
        moveNodeToHead(node);
        return node.value;
    }

    //添加节点
    public void put(String key, String value) {
        Node node = nodeHashMap.get(key);
        if (node == null) { //如果不存在，则添加到链表
            if (nodeHashMap.size() >= capacity) { //大于容量，则需要移除老的数据
                removeNode(tail); //移除尾部节点（tail节点是属于要被淘汰数据）
                nodeHashMap.remove(tail.key); //从hashmap中移除
            }
            node = new Node(key, value);
            nodeHashMap.put(key, node);
            addNodeToHead(node);
        } else {
            //重新赋值
            node.value = value;
            //移动位置到头部
            moveNodeToHead(node);
        }
    }

    class Node {
        private String key;
        private String value;
        Node prev;
        Node next;

        public Node() {
        }

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
