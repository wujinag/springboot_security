package com.example.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * 主界面
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
