package com.owlbuy.owlbuy.security;

import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.model.MemberHasRole;
import com.owlbuy.owlbuy.model.MemberRole;
import com.owlbuy.owlbuy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailService implements UserDetailsService {
    @Autowired
    private MemberService memberService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=memberService.getMemberByEmail(username);
        if(member==null){
            throw new UsernameNotFoundException("尚未註冊");
        }else{
            String email=member.getEmail();
            String password=member.getPassword();
            List<MemberRole>roleList=memberService.getRolesByMemberId(member.getMemberId());

            List<GrantedAuthority>grantedAuthorities=new ArrayList<>();

            for(MemberRole role: roleList){
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }

            return new CustomUserDetail(
                    member.getMemberId(),
                    email,
                    password,
                    grantedAuthorities
            );
        }

    }
}
