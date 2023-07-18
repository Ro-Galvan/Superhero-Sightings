package com.we.SuperHeroSightings.mapper;

import com.we.SuperHeroSightings.entities.Hero;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroMapper implements RowMapper<Hero> {
    @Override
    public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
        Hero hero = new Hero();
        hero.setId(rs.getInt("HeroPK"));
        hero.setName(rs.getString("HeroName"));
        hero.setType(rs.getString("Type"));
        hero.setDescription(rs.getString("Description"));
        return hero;
    }
}
