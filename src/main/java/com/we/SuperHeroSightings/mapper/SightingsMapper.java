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
        
        Hero hero =  new Hero();
        hero.setId(rs.getInt("HeroPK"));
        
        Location location = new Location();
        location.setId(rs.getInt("LocationPK"));
        
        Sighting sighting = new Sighting();
        
        sighting.setId(rs.getInt("SightingPK"));
        sighting.setDate(rs.getObject("SightingDate", LocalDateTime.class)); 
        sighting.setDescription(rs.getString("Description"));
        sighting.setHero(hero);
        sighting.setLocation(location);

        return sighting;
    }
    
}
