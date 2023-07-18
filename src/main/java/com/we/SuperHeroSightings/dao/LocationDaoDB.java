
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.we.SuperHeroSightings.mapper.LocationMapper;
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
public class LocationDaoDB implements LocationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    //retrieves a location object by its ID from the database
    @Override
    public Location getLocationByID(int id) {
        try {
            //executes SQL query and maps result to a Location object using the LocationMapper class
            final String sql = "SELECT * FROM location WHERE LocationPK = ?";
            return jdbc.queryForObject(sql, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    //retrieves list of all Location objects from the database
    @Override
    public List<Location> getAllLocations() {
        //executes SQL query and map the results to a list of Location objects using the LocationMapper class
        final String sql = "SELECT * FROM location";
        return jdbc.query(sql, new LocationMapper());
    }

    //adds new Location object to the database
    @Override
    public Location addLocation(Location location) {
        //executes the SQL statement to insert new location, using provided values from the Location object
        final String sql = "INSERT INTO location(LocationName, Description, LocationAddress, Latitude, Longitude) " + "VALUES(?,?,?,?,?)";
        jdbc.update(sql, location.getName(), location.getDescription(), location.getAddress(), location.getLatitude(), location.getLongitude());

        //retrieves the auto-generated ID of the newly inserted location
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        //sets the ID of the object to the newly generated ID
        location.setId(newId);
        //return the modified Location object
        return location;
    }

    //updates an existing Location object in the database
    @Override
    public void updateLocation(Location location) {
        //executes the SQL statement to update the location with provided values from Location object
        final String sql = "UPDATE location SET LocationName = ?, Description = ?, LocationAddress = ?, Latitude = ?, Longitude = ? WHERE LocationPK = ?";
        jdbc.update(sql, location.getName(), location.getDescription(), location.getAddress(), location.getLatitude(), location.getLongitude(), location.getId());
    }

    //deletes a Location object from the database by its ID
    @Override
    public void deleteLocationByID(int id) {
        //executes SQL statement to delete any sightings associated with the location
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE LocationPK = ?";
        jdbc.update(DELETE_SIGHTING, id);

        //executes the SQL statement to delete the location itself
        final String DELETE_LOCATION = "DELETE FROM location WHERE LocationPK = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) {
        //executes SQL query to retrieve locations associated with the provided Hero
        final String sql = "SELECT l.* FROM location l " +  "JOIN sighting s ON l.LocationPK = s.LocationPK " + "WHERE s.HeroPK = ?";
        return jdbc.query(sql, new LocationMapper(), hero.getId());
    }

    
}
