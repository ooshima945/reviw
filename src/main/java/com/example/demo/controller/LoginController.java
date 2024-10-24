package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.htmlを返す
    }
    /**
     * 管理者および一般ユーザーのログイン処理
     *
     * @param adminLoginForm 管理者ログインフォームのデータ
     * @param generalLoginForm 一般ユーザーログインフォームのデータ
     * @param bindingResult バリデーションエラーの結果
     * @param model モデルオブジェクト
     * @return ログイン成功時のリダイレクト先
     */
    @PostMapping("/users/login")
    public String loginSubmit(
            @RequestParam("username") String username,  // ユーザー名やメールアドレスの入力を受け取る
            @RequestParam("password") String password,  // パスワードの入力を受け取る
            Model model) {



        try {
            Authentication authentication;

            if (username.contains("@")) {
                // 管理者ログイン処理
                authentication = loginService.authenticate(username, password); // 管理者の認証処理
     
            } else {
                // 一般ユーザーログイン処理
                authentication = loginService.authenticate(username, password); // 一般ユーザーの認証処理
     
            }

            // 認証が成功したらセッションに認証情報を設定
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // ログインユーザーが管理者か一般ユーザーかを判定してリダイレクト
            if (username.contains("@")) {
          
                return "redirect:/dashboard"; // 管理者ログイン成功時にダッシュボードへリダイレクト
            } else {
                
                return "redirect:/main"; // 一般ユーザーログイン成功時にメインページへリダイレクト
            }

        } catch (Exception e) {
            // 認証失敗時のエラーメッセージを追加
            model.addAttribute("loginError", "ユーザー名またはパスワードが間違っています");
          
            return "login"; // ログインページにエラー付きで戻る
        }
    }

    }

