package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.dao.MemberDao;
import com.owlbuy.owlbuy.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemberDaoImpl implements MemberDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createMember(Member member) {
        String sql="INSERT INTO member(member_name,email,password,created_date)VALUES(:member_name,:email,:password,:created_date)";
        Map<String,Object> map=new HashMap<>();
        map.put("member_name",member.getMemberName());
        map.put("email",member.getEmail());
        map.put("password",member.getPassword());
        map.put("created_date",member.getCreatedDate());

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));

    }

    @Override
    public Member getMemberById(Integer memberId) {
        return null;
    }
}
