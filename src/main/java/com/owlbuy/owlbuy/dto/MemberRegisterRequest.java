package com.owlbuy.owlbuy.dto;

import jakarta.validation.constraints.*;

import java.util.Date;

public class MemberRegisterRequest {
    @NotBlank(message = "姓名不得為空")
    private String memberName;

    @NotBlank(message = "信箱不得為空")
    @Email(message = "信箱格式不正確")
    private String email;

    @NotBlank(message = "密碼不得為空")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "密碼必須包含至少一個英文字母與一個數字"
    )
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
