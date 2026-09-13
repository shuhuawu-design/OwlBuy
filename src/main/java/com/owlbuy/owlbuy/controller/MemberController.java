package com.owlbuy.owlbuy.controller;

import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Member member){
        String hashPassword =passwordEncoder.encode(member.getPassword());
        member.setPassword(hashPassword);

        memberService.createMember(member);

        return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功");

    }
}
