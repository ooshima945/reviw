package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminUser;
import com.example.demo.form.AdminSignupForm;
import com.example.demo.repository.AdminUserRepository;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 新しい管理者を登録するロジック
     *
     * @param adminSignupForm 登録フォームから取得したデータ
     */
    public void registerAdmin(AdminSignupForm adminSignupForm) {
        if (adminUserRepository.findByAdminEmail(adminSignupForm.getAdminEmail()).isPresent()) {
            throw new IllegalArgumentException("このメールアドレスはすでに使用されています。");
        }

        // 新しい管理者を作成
        AdminUser newAdmin = new AdminUser();
        newAdmin.setAdminEmail(adminSignupForm.getAdminEmail());
        newAdmin.setAdminPassword(passwordEncoder.encode(adminSignupForm.getAdminPassword()));  // パスワードをエンコード

        // 管理者情報を保存
        adminUserRepository.save(newAdmin);
    }
   
    
}
