package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminLoginForm {

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String adminEmail;

    @NotEmpty(message = "Password is required")
    private String adminPassword;
}
