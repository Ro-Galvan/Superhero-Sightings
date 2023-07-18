package com.we.SuperHeroSightings.mapper;


import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;


public class SightingsMapper implements RowMapper<Sighting>{

    @Override
    public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        Hero character =  new Hero();
        character.setId(rs.getInt("superID"));
        
        Location location = new Location();
        location.setId(rs.getInt("locationID"));
        
        Sighting sighting = new Sighting();
        
        sighting.setId(rs.getInt("sightingID"));
        sighting.setDate(rs.getObject("dateSighted", LocalDateTime.class));     
        sighting.setHero(character);
        sighting.setLocation(location);

        return sighting;
    }
    
}
