package com.example.demo.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class GeneralUserDetails implements UserDetails {

    private Integer generalId;  // 一般ユーザーID
    private String generalUserLoginId;  // ログインID
    private String generalUserPassword;  // パスワード
    private Collection<? extends GrantedAuthority> authorities;  // 権限情報

    // コンストラクタで初期化
    public GeneralUserDetails(Integer generalId, String generalUserLoginId, String generalUserPassword, Collection<? extends GrantedAuthority> authorities) {
        this.generalId = generalId;
        this.generalUserLoginId = generalUserLoginId;
        this.generalUserPassword = generalUserPassword;
        this.authorities = authorities;
    }

    // 一般ユーザーIDを取得
    public Integer getGeneralId() {
        return generalId;
    }

    @Override
    public String getUsername() {
        return generalUserLoginId;
    }

    @Override
    public String getPassword() {
        return generalUserPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // アカウントがロックされていない状態を示す
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // アカウントの有効期限が切れていないことを示す
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 認証情報が期限切れではないことを示す
    }

    @Override
    public boolean isEnabled() {
        return true;  // アカウントが有効であることを示す
    }
}

