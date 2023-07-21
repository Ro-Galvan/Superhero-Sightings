
package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.SightingDaoDB;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SightingsServiceImpl implements SightingService{
    
    @Autowired
    SightingDaoDB sightingsDao;

    @Override
    public Sighting getSightingByID(int id) {
        Sighting sighting = sightingsDao.getSightingByID(id);
        if (sighting == null){
            throw new RuntimeException("Could not find the sighting with the id " +id+".");
        }
        return sighting;
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingsDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingsDao.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightingsDao.updateSighting(sighting);
    }

    @Override
    public void deleteSightingByID(int id) {
        sightingsDao.deleteSightingByID(id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        return sightingsDao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        return sightingsDao.getSightingsByLocation(location);
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        return sightingsDao.getSightingsByHero(hero);
    }
    
}
