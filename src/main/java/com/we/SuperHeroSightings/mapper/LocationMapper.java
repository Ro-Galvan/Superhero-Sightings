package com.we.SuperHeroSightings.mapper;

import com.we.SuperHeroSightings.entities.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();

        location.setId(rs.getInt("locationID"));
        location.setName(rs.getString("locationName"));
        location.setDescription(rs.getString("locationDesc"));
        location.setAddress(rs.getString("locationAddress"));
        location.setLatitude(rs.getString("Latitude"));
        location.setLongitude(rs.getString("longitude"));

        return location;
    }
}
