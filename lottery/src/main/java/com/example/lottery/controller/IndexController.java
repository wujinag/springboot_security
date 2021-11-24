package com.example.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        //  K  {V: String /HashTable(K,V) /ArrayList/ Set}
        // List<Prize>
        // JSON.toJSON(list);   string
        // HashTable<K,V>
        return "index";
    }
}
