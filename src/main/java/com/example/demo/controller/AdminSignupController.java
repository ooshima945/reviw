package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.AdminSignupForm;
import com.example.demo.service.AdminUserService;

import jakarta.validation.Valid;

@Controller
public class AdminSignupController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/adminsignup")
    public String showAdminSignupPage(Model model) {
        // フォーム初期化
        model.addAttribute("adminSignupForm", new AdminSignupForm());
        return "adminsignup"; // adminsignup.htmlを表示
    }
    @PostMapping("/adminuser/create")
    public String adminSignupSubmit(
        @ModelAttribute("adminSignupForm") @Valid AdminSignupForm adminSignupForm,
        BindingResult bindingResult,
        Model model) {

        // バリデーションエラーがあればフォームを再表示
        if (bindingResult.hasErrors()) {
            return "adminsignup"; // templates/adminsignup.html を再表示
        }

        try {
          
            // 管理者を登録
            adminUserService.registerAdmin(adminSignupForm);
        } catch (IllegalArgumentException e) {
            // 登録失敗時のエラーメッセージを追加
            model.addAttribute("signupError", e.getMessage());
            return "adminsignup"; // エラー時は再度フォームを表示
        }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
            // 同じメールアドレスが存在する場合のエラーメッセージを追加
           model.addAttribute("signupError", "指定されたアカウントは既に存在します。");
            return "adminsignup"; // エラー時は再度フォームを表示
        }


        // 登録が成功したらログインページにリダイレクト
        return "redirect:/login";
    }
}



