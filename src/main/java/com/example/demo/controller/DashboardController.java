package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.config.AdminUserDetails;
import com.example.demo.entity.GeneralUser;
import com.example.demo.form.GeneralSignupForm;
import com.example.demo.service.GeneralUserService;
@Controller
public class DashboardController {
	@Autowired
    private final GeneralUserService generalUserService;

    public DashboardController(GeneralUserService generalUserService) {
        this.generalUserService = generalUserService;
    }

 

    @GetMapping("/dashboard")
    public String redirectDashboard(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        return "redirect:/dashboard/show?page=" + page;
    }
    // ダッシュボード表示: /dashboard/show に GET リクエストでアクセス
    @GetMapping("/dashboard/show")
    public String showDashboard(
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {
    	model.addAttribute("generalSignupForm", new GeneralSignupForm());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();
        Integer adminId = adminUserDetails.getAdminId();

        PageRequest pageable = PageRequest.of(page, 5); // PageRequestオブジェクトを作成
       Page<GeneralUser> generalUsers = generalUserService.findByAdminUserAdminIdAndDeletedAtIsNull(adminId, pageable); 

        // モデルにページ情報を追加
        model.addAttribute("generalUsers", generalUsers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", generalUsers.getTotalPages());

        return "dashboard"; // dashboard.html をレンダリング
    }

    // ユーザー追加処理
    @PostMapping("/users/create")
    public String createGeneralUser(
            @ModelAttribute("generalSignupForm") GeneralSignupForm generalSignupForm,
            RedirectAttributes redirectAttributes) {

        // ログインしている管理者のEmailを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminEmail = authentication.getName();

        // 一般ユーザーを作成
        generalUserService.createGeneralUserByAdminEmail(adminEmail, generalSignupForm.getGeneralPassword());

     

        return "redirect:/dashboard"; 
    }
    @PostMapping("/users/delete")
    public String deleteGeneralUser(
        @RequestParam(name = "userId", required = false) Short loginId,
        RedirectAttributes redirectAttributes) {

     
            // userIdがnullでない場合は削除処理を実行
           generalUserService.deleteGeneralUserByLoginId(loginId);


       return "redirect:/dashboard";
    }
    
}