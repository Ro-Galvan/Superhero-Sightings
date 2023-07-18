package com.we.SuperHeroSightings.mapper;

import com.we.SuperHeroSightings.entities.Power;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PowerMapper implements RowMapper<Power> {
    @Override
    public Power mapRow(ResultSet rs, int rowNum) throws SQLException {
        Power power = new Power();
        power.setId(rs.getInt("PowerPK"));
        power.setName(rs.getString("Power"));
        power.setDescription(rs.getString("Description"));
        return power;
    }
}
