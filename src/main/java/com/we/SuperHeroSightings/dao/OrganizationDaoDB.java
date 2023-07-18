
package com.we.SuperHeroSightings.dao;


import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;

import java.sql.*;
import java.util.List;

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
            final String sql = "SELECT OrganizationPK, OrganizationName, `Type`, `Description`, OrganizationAddress, Phone, ContactInfo "
                    +"FROM `organization` "
                    +"WHERE OrganizationPK = ?";
            return jdbc.queryForObject(sql, new OrganizationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String sql = "SELECT * FROM organization";
        return jdbc.query(sql, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String sql = "INSERT INTO `organization`(OrganizationName, `Type`, `Description`, OrganizationAddress, Phone, ContactInfo) "
                +"VALUES(?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn ) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, organization.getName());
            statement.setString(2, organization.getType());
            statement.setString(3, organization.getDescription());
            statement.setString(4, organization.getAddress());
            statement.setString(5, organization.getPhone());
            statement.setString(6, organization.getContact());
            return statement;
        }, keyHolder);

        organization.setId(keyHolder.getKey().intValue());

        final String bridgeSql = "INSERT INTO heroorganization (HeroPK, OrganizationPK) VALUES (?, ?)";

        List<Hero> heroes = organization.getMembers();

        if (heroes != null) {
            for (Hero hero : heroes) {
                jdbc.update(bridgeSql, hero.getId(), organization.getId());
            }
        }

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
        final String sql = "SELECT org.OrganizationPK, org.OrganizationName, org.`Type`, org.`Description`, org.OrganizationAddress, org.Phone, org.ContactInfo "
                +"FROM `organization` org "
                +"INNER JOIN heroorganization horg ON org.OrganizationPK = horg.OrganizationPK "
                +"WHERE horg.HeroPK = ?";

        List<Organization> organizations = jdbc.query(sql, new Object[]{hero.getId()}, new OrganizationMapper());

        return organizations;
    }

}
