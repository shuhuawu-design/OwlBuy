package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.dao.MemberDao;
import com.owlbuy.owlbuy.dto.MemberRegisterRequest;
import com.owlbuy.owlbuy.model.Member;
import com.owlbuy.owlbuy.model.MemberHasRole;
import com.owlbuy.owlbuy.model.MemberRole;
import com.owlbuy.owlbuy.rowmapper.MemberRoleRowMapper;
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
    public Integer createMember(MemberRegisterRequest memberRegisterRequest) {
        String sql="INSERT INTO member(member_name,email,password)VALUES(:member_name,:email,:password)";
        Map<String,Object> map=new HashMap<>();
        map.put("member_name",memberRegisterRequest.getMemberName());
        map.put("email",memberRegisterRequest.getEmail());
        map.put("password",memberRegisterRequest.getPassword());

        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer memberId=keyHolder.getKey().intValue();
        return memberId;
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

    @Override
    public List<MemberRole> getRolesByMemberId(Integer memberId) {
        String sql="""
            SELECT r.role_id, r.role_name
            FROM member_role As r
            JOIN member_has_role AS h ON h.role_id = r.role_id
            WHERE h.member_id=:member_id
        """;
        Map<String,Object> map=new HashMap<>();
        map.put("member_id",memberId);

        return namedParameterJdbcTemplate.query(sql,map,new MemberRoleRowMapper());

    }

    @Override
    public MemberRole getRoleByRoleName(String roleName) {
        String sql="SELECT role_id, role_name FROM member_role WHERE role_name=:role_name";

        Map<String,Object> map=new HashMap<>();
        map.put("role_name",roleName);

        List<MemberRole>role=namedParameterJdbcTemplate.query(sql,map,new MemberRoleRowMapper());
        if(role.size()>0){
            return role.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void addRoleForMemberId(Integer memberId, MemberRole role) {
        String sql="INSERT INTO member_has_role(member_id , role_id) VALUES (:member_id,:role_id)";
        Map<String,Object> map=new HashMap<>();
        map.put("member_id",memberId);
        map.put("role_id",role.getRoleId());

        namedParameterJdbcTemplate.update(sql,map);
    }
}
