package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;


import java.util.List;

public interface HeroService {
    Hero getHeroByID(int id);
    List<Hero> getAllHeroes();
    Hero addHero(Hero hero);
    void updateHero(Hero hero);
    void deleteHeroByID(int id);

    List<Hero> getHeroesByLocation(Location location);
    List<Hero> getHeroesByOrganization(Organization organization);
}
