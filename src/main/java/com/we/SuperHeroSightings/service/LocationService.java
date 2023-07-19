package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Location;

import java.util.List;

public interface LocationService {
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int id);
}
