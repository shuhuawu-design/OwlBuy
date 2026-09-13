package com.owlbuy.owlbuy.service;

import com.owlbuy.owlbuy.model.Member;

public interface MemberService {
    void createMember(Member member);
    Member getMemberById(Integer memberId);
}
