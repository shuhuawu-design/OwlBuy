package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.model.Member;

public interface MemberDao {
    void createMember(Member member);
    Member getMemberById(Integer memberId);
}
