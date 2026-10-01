package com.owlbuy.owlbuy.dao;

import com.owlbuy.owlbuy.dto.MemberRegisterRequest;
import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.model.MemberRole;

import java.util.List;

public interface MemberDao {
    Integer createMember(MemberRegisterRequest memberRegisterRequest);
    Member getMemberByEmail(String email);
    List<MemberRole> getRolesByMemberId(Integer memberId);
    MemberRole getRoleByRoleName(String roleName);
    void addRoleForMemberId(Integer memberId,MemberRole role);
}
