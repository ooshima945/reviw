package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.config.AdminUserDetails;
import com.example.demo.service.GeneralUserService;

@Controller
public class DashboardController {

    @Autowired
    private GeneralUserService generalUserService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // 現在の認証情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof AdminUserDetails) {
            // AdminUserDetails から adminId を取得
            AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
            Integer adminId = userDetails.getAdminId();

            // adminId を使って general_users を検索し、コンソールに表示
            generalUserService.showGeneralUsersByAdminId(adminId);

            // 他の処理がある場合はここに記述

            return "dashboard";  // ダッシュボードのHTMLページ
        }

        // 認証情報が不正な場合、ログインページへリダイレクト
        return "redirect:/login";
    }
    }