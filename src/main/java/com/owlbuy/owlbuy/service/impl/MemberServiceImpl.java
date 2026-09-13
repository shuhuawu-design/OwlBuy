package com.owlbuy.owlbuy.service.impl;

import com.owlbuy.owlbuy.dao.MemberDao;
import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public void createMember(Member member) {
        memberDao.createMember(member);
    }

    @Override
    public Member getMemberById(Integer memberId) {
        return memberDao.getMemberById(memberId);
    }
}
