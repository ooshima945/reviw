package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String redirectToIndex() {
        // "/"にアクセスされた場合 "/index" にリダイレクト
        return "/index";
    }
    @PostMapping("/login")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    /**
     * 新規登録ボタンを押したときの処理
     * @return 新規登録ページにリダイレクト
     */
    @PostMapping("/adminsignup")
    public String redirectToSignup() {
        return "redirect:/adminsignup";
    }
}