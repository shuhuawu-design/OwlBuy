package com.owlbuy.owlbuy.rowmapper;

import com.owlbuy.owlbuy.model.MemberRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRoleRowMapper implements RowMapper<MemberRole> {
    @Override
    public MemberRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberRole memberRole = new MemberRole();
        memberRole.setRoleId(rs.getInt("role_id"));
        memberRole.setRoleName(rs.getString("role_name"));
        return memberRole;
    }
}
