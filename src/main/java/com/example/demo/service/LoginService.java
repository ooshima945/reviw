package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    @Qualifier("adminUserDetailsService")
    private UserDetailsService adminUserDetailsService;

    @Autowired
    @Qualifier("generalUserDetailsService")
    private UserDetailsService generalUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 管理者または一般ユーザーの認証を処理します。
     * @param username ユーザー名またはメールアドレス
     * @param password パスワード
     * @return 認証済みの Authentication オブジェクト
     */
    public Authentication authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
    }

    /**
     * 管理者ログインの判定
     * @param email 管理者のメールアドレス
     * @return boolean 管理者の場合は true
     */
    public boolean isAdmin(String email) {
        return email.contains("@"); // 簡単なロジック、メールアドレスで判断
    }
}


