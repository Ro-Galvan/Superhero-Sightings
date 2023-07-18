
package com.we.SuperHeroSightings.dao;


import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;

import java.sql.*;
import java.util.List;

import com.we.SuperHeroSightings.mapper.HeroMapper;
import com.we.SuperHeroSightings.mapper.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jtriolo
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationByID(int id) {
        try {
            final String sql = "SELECT * FROM `organization` WHERE OrganizationPK = ?";
            Organization org = jdbc.queryForObject(sql, new OrganizationMapper(), id);
            org.setMembers(getHeroesByOrganization(org));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Hero> getHeroesByOrganization(Organization organization){
        final String sql = "SELECT DISTINCT h.* "
                +"FROM hero h "
                +"INNER JOIN heroorganization ho "
                +"ON h.heroPK = ho.heroPK "
                +"WHERE organizationPK = ?";
        return  jdbc.query(sql, new HeroMapper(), organization.getId());
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String sql = "SELECT * FROM organization";
        List<Organization> orgs = jdbc.query(sql, new OrganizationMapper());
        for (Organization org : orgs) {
            org.setMembers(getHeroesByOrganization(org));
        }
        return orgs;
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String sql = "INSERT INTO `organization`(OrganizationName, `Type`, `Description`, OrganizationAddress, Phone, ContactInfo) "
                +"VALUES(?, ?, ?, ?, ?, ?)";

        jdbc.update(sql,
                organization.getName(),
                organization.getType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getContact());

        int orgId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(orgId);
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String sql = "UPDATE `organization` SET "
                +"OrganizationName = ?, "
                +"`Type` = ?, "
                +"`Description` = ?, "
                +"OrganizationAddress = ?, "
                +"Phone = ?, "
                +"ContactInfo = ? "
                +"WHERE OrganizationPK = ?";
        jdbc.update(sql,
                organization.getName(),
                organization.getType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getContact(),
                organization.getId());
    }

    @Override
    public void deleteOrganizationByID(int id) {
        final String hOrgSql = "DELETE FROM heroorganization WHERE OrganizationPK = ?";
        jdbc.update(hOrgSql, id);
        final String orgSql = "DELETE FROM `organization` WHERE OrganizationPK = ?";
        jdbc.update(orgSql, id);
    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        final String sql = "SELECT o.*"
                +"FROM `organization` o "
                +"INNER JOIN heroorganization ho ON o.OrganizationPK = ho.OrganizationPK "
                +"WHERE ho.HeroPK = ?";

        List<Organization> organizations = jdbc.query(sql, new OrganizationMapper(), hero.getId());

        return organizations;
    }

}
