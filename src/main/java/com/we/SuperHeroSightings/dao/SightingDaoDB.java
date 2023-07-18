
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import com.we.SuperHeroSightings.mapper.HeroMapper;
import com.we.SuperHeroSightings.mapper.LocationMapper;
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
            return jdbc.queryForObject("SELECT * FROM sighting WHERE SightingPK = ?", new SightingsMapper(), id);
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
            final String sqlInsertSighting = 
                    "INSERT INTO Sighting(SightingDate, Description, HeroPK, LocationPK) "
                    + "VALUES(?, ?, ?, ?)";
            
            jdbc.update(sqlInsertSighting, 
                    sighting.getDate(),
                    sighting.getDescription(),                    
                    sighting.getHero().getId(), 
                    sighting.getLocation().getId());
            
            int lastID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            
            sighting.setId(lastID);
            
            final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE LocationPK = ?";
            final String GET_HERO_BY_ID = "SELECT * FROM Hero WHERE HeroPK = ?";
            
            Location location = jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), 
                                            sighting.getLocation().getId());
            
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), 
                                        sighting.getHero().getId());
            
            sighting.setHero(hero);
            sighting.setLocation(location);
            
            return sighting;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String sqlUpdate = 
                "UPDATE Sighting SET SightingDate = ?, Description = ? WHERE SightingPK = ?;";
        
        jdbc.update(sqlUpdate, 
                sighting.getDate(), 
                sighting.getDescription(), 
                sighting.getId());
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
