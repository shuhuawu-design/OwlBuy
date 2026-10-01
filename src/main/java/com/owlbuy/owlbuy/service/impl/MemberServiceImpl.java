package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.MemberDao;
import com.owlbuy.owlbuy.dto.MemberRegisterRequest;
import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.model.MemberRole;
import com.owlbuy.owlbuy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public void createMember(MemberRegisterRequest memberRegisterRequest) {
        Integer memberId=memberDao.createMember(memberRegisterRequest);
        MemberRole normalRole=memberDao.getRoleByRoleName("ROLE_NORMAL");
        memberDao.addRoleForMemberId(memberId,normalRole);
    }

    @Override
    public Member getMemberByEmail(String email) {
        return memberDao.getMemberByEmail(email);
    }

    @Override
    public List<MemberRole> getRolesByMemberId(Integer memberId) {
        return memberDao.getRolesByMemberId(memberId);
    }
}
