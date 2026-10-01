package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.dto.MemberRegisterRequest;
import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.model.MemberRole;

import java.util.List;

public interface MemberService {
    void createMember(MemberRegisterRequest memberRegisterRequest) ;
    Member getMemberByEmail(String email);
    List<MemberRole> getRolesByMemberId(Integer memberId);


}
