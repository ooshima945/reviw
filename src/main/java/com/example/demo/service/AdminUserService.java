package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        AdminUser newAdmin = new AdminUser();
        newAdmin.setAdminEmail(adminSignupForm.getAdminEmail());
        newAdmin.setAdminPassword(passwordEncoder.encode(adminSignupForm.getAdminPassword()));
       

        // 管理者情報を保存
        adminUserRepository.save(newAdmin);
    }
    public Integer getAdminIdByEmail(String adminEmail) {
        // AdminUserのリポジトリを使用して、adminEmailに基づいてadminIdを取得
        AdminUser adminUser = adminUserRepository.findByAdminEmail(adminEmail)
            .orElseThrow(() -> new IllegalArgumentException("管理者が見つかりません: " + adminEmail));
        return adminUser.getAdminId();
    }
    public AdminUser findByAdminId(Integer adminId) {
        return adminUserRepository.findById(adminId)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with id: " + adminId));
    }
    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }
}
