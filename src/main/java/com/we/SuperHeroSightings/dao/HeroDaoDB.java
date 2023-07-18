
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.we.SuperHeroSightings.mapper.HeroMapper;
import com.we.SuperHeroSightings.mapper.OrganizationMapper;
import com.we.SuperHeroSightings.mapper.PowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jtriolo
 */
@Repository
public class HeroDaoDB implements HeroDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroByID(int id) {
        try {
            final String SQL = "SELECT * FROM hero WHERE HeroPK = ?";
            Hero hero = jdbc.queryForObject(SQL, new HeroMapper(), id);
            hero.setPower(getPowerForHero(id));
            hero.setOrganizations(getOrganizationsForHero(id));
            return hero;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeros() {
        final String SQL = "SELECT * FROM hero";
        List<Hero> heroList = jdbc.query(SQL, new HeroMapper());
//        TODO -am I missing calling one of the helper methods here before the return???
        return heroList;
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String SQL = "INSERT INTO hero(HeroName, Type, Description, PowerPK) VALUES(?,?,?,?)";
        jdbc.update(SQL,
                hero.getName(),
                hero.getType(),
                hero.getDescription(),
                hero.getPower().getId());

        // Retrieve the last inserted ID
        String selectLastIdQuery = "SELECT LAST_INSERT_ID()";
        int newId = jdbc.queryForObject(selectLastIdQuery, Integer.class);
        hero.setId(newId);
        insertHeroOrganization(hero);
        return hero;
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        final String SQL = "UPDATE hero SET HeroName = ?, Type = ?, Description = ?, PowerPK = ? WHERE HeroPK = ?";
        jdbc.update(SQL,
                hero.getId(),
                hero.getType(),
                hero.getDescription(),
                hero.getPower().getId(),
                hero.getId());

        final String DELETE_HERO_ORGANIZATION = "DELETE FROM heroorganization WHERE HeroPK = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, hero.getId());
        insertHeroOrganization(hero);
    }

    @Override
    @Transactional
    public void deleteHeroByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Hero> getHerosByLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Hero> getHerosByOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //    ******************* private helper methods ********************
    private Power getPowerForHero(int id){
        final String SQL = "SELECT p.* FROM hero h " +
                "JOIN power p ON h.PowerPK = p.PowerPK WHERE h.HeroName = ?;";
        return jdbc.queryForObject(SQL, new PowerMapper(), id);
    }

    private List<Organization> getOrganizationsForHero(int id){
        final String SQL = "SELECT o.*" +
                "FROM hero h" +
                "JOIN heroorganization ho ON h.HeroPK = ho.HeroPK " +
                "JOIN organization o ON ho.OrganizationPK = o.OrganizationPK " +
                "WHERE h.HeroName = 'YourHeroName';";
        return jdbc.query(SQL, new OrganizationMapper(), id);
    }

    private void insertHeroOrganization(Hero hero){
        final String SQL = "INSERT INTO heroorganization (HeroPK, OrganizationPK) " +
                "VALUES (?, ?);";
        for(Organization organization : hero.getOrganizations()) {
            jdbc.update(SQL,
                    hero.getId(),
                    organization.getId());
        }
    }

}
