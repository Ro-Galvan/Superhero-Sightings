
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
public class PowerDaoDB implements PowerDao {
    
    @Autowired
    JdbcTemplate jdbc;


    //-------------- getPowerByID --------------//
    @Override
    public Power getPowerByID(int id) {

        try {
            // Executes a SQL query to retrieve a power by its ID from the "power" table.
            final String SELECT_POWER_BY_ID = "SELECT * FROM power WHERE PowerPK = ?";
            // Uses a custom "PowerMapper" class to map the query result to a Superpower object.
            return jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
            // In case of any exception or data access error, returns null.
        }
    }

    //-------------- getAllPowers() --------------//
    @Override
    public List<Power> getAllPowers() {
        //-Executes a SQL query to retrieve all powers from the "power" table.
        final String SELECT_ALL_POWERS = "SELECT * FROM power";
        //-Uses a custom "PowerMapper" class to map the query result to a list of power objects.
        return jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
    }

    //-------------- addPower() --------------//
    @Override
    @Transactional
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO power(Power, Description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_POWER,
                power.getName(),
                power.getDescription());
        // Executes an SQL INSERT statement to add a new power to the "power" table.
        // Uses the values of name and description from the provided Power object as parameters for the INSERT statement.


        // Retrieves the ID of the newly inserted power.
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        // Sets the newly generated ID in the Power object.
        power.setId(newId);

        // Returns the Power object with the updated ID.
        return power;
    }

    //------------------ updatePower() -----------------//
    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE power SET Power = ?, Description = ? "
                + "WHERE PowerPK = ?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getDescription(),
                power.getId());
        // Executes an SQL UPDATE statement to update the power's name and description in the "power" table.
        // Uses the values of name, description, and ID from the provided Power object as parameters for the UPDATE statement.
    }

    //------------------ deletePowerByID() -----------------//
    @Override
    @Transactional
    public void deletePowerByID(int id) {
        // Delete heroes and hero-organization associations that reference the power.
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM heroorganization WHERE HeroPK IN (SELECT HeroPK FROM hero WHERE PowerPK = ?)";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);

        final String DELETE_HERO = "DELETE FROM hero WHERE PowerPK = ?";
        jdbc.update(DELETE_HERO, id);

        // Executes an SQL DELETE statement to delete a power from the "power" table based on its ID.
        final String DELETE_POWER = "DELETE FROM power WHERE PowerPK = ?";
        // Uses the id parameter as a parameter for the DELETE statement.
        jdbc.update(DELETE_POWER, id);


    }
    

}
