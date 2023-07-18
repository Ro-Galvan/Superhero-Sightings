package com.we.SuperHeroSightings.mapper;

import com.we.SuperHeroSightings.entities.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrganizationMapper implements RowMapper<Organization> {
    @Override
    public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
        Organization org = new Organization();
        org.setId(rs.getInt("OrganizationPK"));
        org.setName(rs.getString("OrganizationName"));
        org.setType(rs.getString("Type"));
        org.setDescription(rs.getString("Description"));
        org.setAddress(rs.getString("OrganizationAddress"));
        org.setPhone(rs.getString("Phone"));
        org.setContact(rs.getString("ContactInfo"));
        return org;
    }
}
