package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class AdminSignupForm {
    @NotEmpty(message = "メールアドレスを入力してください")
   
    private String adminEmail;

    @NotEmpty(message = "パスワードを入力してください")
    private String adminPassword;

}