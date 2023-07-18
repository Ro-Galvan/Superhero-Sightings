
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import com.we.SuperHeroSightings.mapper.HeroMapper;
import com.we.SuperHeroSightings.mapper.SightingsMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SightingDaoDB implements SightingDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingByID(int id) {
        try{
            return jdbc.queryForObject("SELECT * FROM sighting WHERE sightingID = ?", new SightingsMapper(), id);
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        try{
            final String sql = "SELECT * FROM Sighting";
            return jdbc.query(sql, new SightingsMapper());
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        try{
            final String sqlInsertSighting = "INSERT INTO Sighting(SightingDate, LocationPK, HeroPK) VALUES(?, ?, ?);";
            
            jdbc.update(sqlInsertSighting, 
                    sighting.getDate(),
                    sighting.getLocation().getId(),
                    sighting.getHero().getId());
            
            int lastID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            
            Sighting sightingInserted = getSightingByID(lastID);
            //Hero hero = 
            //sightingInserted.setCharacter(character);
            //sightingInserted.setLocation(location);
            
            return sightingInserted;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String sqlUpdate = "";
        jdbc.update(sqlUpdate);
    }

    @Override
    public void deleteSightingByID(int id) {
        final String sql = "DELETE FROM Sighting WHERE SightingPK = ?";
        jdbc.update(sql, id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        try{
            final String sql = "SELECT * FROM Sighting WHERE SightingDate = ?;";
            return jdbc.query(sql, new SightingsMapper(), date);
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        try{
            final String sql = "SELECT * FROM Sighting WHERE LocationPK = ?;";
            return jdbc.query(sql, new SightingsMapper(), location.getId());
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        try{
            final String sql = "SELECT * FROM Sighting WHERE HeroPK = ?;";
            return jdbc.query(sql, new SightingsMapper(), hero.getId());
        }
        catch (DataAccessException ex){
            return null;
        }
    }
    
}
