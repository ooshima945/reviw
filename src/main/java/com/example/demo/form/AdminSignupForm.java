package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;

public class AdminSignupForm {
    @NotEmpty(message = "メールアドレスを入力してください")
   
    private String adminEmail;

    @NotEmpty(message = "パスワードを入力してください")
    private String adminPassword;



	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}




}