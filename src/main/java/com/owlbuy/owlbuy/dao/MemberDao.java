package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.dto.MemberRegisterRequest;
import com.owlbuy.owlbuy.model.Member;

public interface MemberDao {
    void createMember(MemberRegisterRequest memberRegisterRequest);
    Member getMemberByEmail(String email);
}
