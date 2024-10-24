package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.config.AdminUserDetails;
import com.example.demo.entity.GeneralUser;
import com.example.demo.service.GeneralUserService;
@Controller
public class DashboardController {

    @Autowired
    private GeneralUserService generalUserService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // ログインしているadminIdを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();
        Integer adminId = adminUserDetails.getAdminId();

        // adminIdに基づいて一般ユーザーを取得
        List<GeneralUser> generalUsers = generalUserService.findByAdminId(adminId);
        model.addAttribute("generalUsers", generalUsers);
        return "dashboard"; // dashboard.html を表示
    }
       @PostMapping("/users/delete")
    public String deleteGeneralUser(@RequestParam("userId") short generalId, RedirectAttributes redirectAttributes) {
        try {
            // 指定されたIDのユーザーを削除するサービスメソッドの呼び出し
            generalUserService.deleteGeneralUserById(generalId);
            redirectAttributes.addFlashAttribute("message", "ユーザーID " + generalId + " が正常に削除されました。");
        } catch (IllegalArgumentException e) {
            // エラーメッセージをフラッシュアトリビュートに追加し、エラーページにリダイレクト
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        // ダッシュボードページにリダイレクト
        return "redirect:/dashboard";
    }
}
