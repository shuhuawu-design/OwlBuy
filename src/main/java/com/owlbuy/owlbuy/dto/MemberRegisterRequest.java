package com.owlbuy.owlbuy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class MemberRegisterRequest {
    @NotBlank(message = "姓名不得為空")
    private String memberName;
    @NotBlank(message = "信箱不得為空")
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 12, message = "密碼須為8~12字元之間")
    private String password;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
