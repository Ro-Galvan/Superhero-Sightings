package com.we.SuperHeroSightings.mapper;


import com.we.SuperHeroSightings.entities.Hero;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class HeroMapper implements RowMapper<Hero> {

    @Override
    public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
        Hero mapperSuper = new Hero();
        mapperSuper.setId(rs.getInt("superID"));
        mapperSuper.setName(rs.getString("superName"));
        mapperSuper.setDescription(rs.getString("superDesc"));
        mapperSuper.setEvil(rs.getBoolean("isEvil"));
        mapperSuper.setPower(rs.getInt("superpowerID"));
        return mapperSuper;
    }
}
