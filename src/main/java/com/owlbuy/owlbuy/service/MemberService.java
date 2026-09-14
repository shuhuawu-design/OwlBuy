package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.MemberRegisterRequest;
import com.owlbuy.owlbuy.model.Member;

public interface MemberService {
    void createMember(MemberRegisterRequest memberRegisterRequest) ;
    Member getMemberByEmail(String email);
}
