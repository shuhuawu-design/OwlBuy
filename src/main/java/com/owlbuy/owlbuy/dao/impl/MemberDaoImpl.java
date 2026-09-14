package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.dao.MemberDao;
import com.owlbuy.owlbuy.dto.MemberRegisterRequest;
import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.rowmapper.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemberDaoImpl implements MemberDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createMember(MemberRegisterRequest memberRegisterRequest) {
        String sql="INSERT INTO member(member_name,email,password)VALUES(:member_name,:email,:password)";
        Map<String,Object> map=new HashMap<>();
        map.put("member_name",memberRegisterRequest.getMemberName());
        map.put("email",memberRegisterRequest.getEmail());
        map.put("password",memberRegisterRequest.getPassword());

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));

    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql="SELECT member_id, member_name, email, password, created_date FROM member WHERE email=:email";
        Map<String,Object> map=new HashMap<>();
        map.put("email",email);

        List<Member> member=namedParameterJdbcTemplate.query(sql,map,new MemberRowMapper());
        if(member.size()>0){
            return member.get(0);
        }else{
            return null;
        }
    }
}
