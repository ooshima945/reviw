package com.example.demo.config;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminUserDetails implements UserDetails {

    private Integer adminId;  // 管理者ID
    private String adminEmail;
    private String adminPassword;
    private Collection<? extends GrantedAuthority> authorities;

    public AdminUserDetails(Integer adminId, String adminEmail, String adminPassword, Collection<? extends GrantedAuthority> authorities) {
        this.adminId = adminId;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.authorities = authorities;
    }

    // 管理者IDを取得
    public Integer getAdminId() {
        return adminId;
    }

    @Override
    public String getUsername() {
        return adminEmail;
    }

    @Override
    public String getPassword() {
        return adminPassword;
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