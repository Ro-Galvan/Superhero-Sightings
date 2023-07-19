package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;

import java.util.List;

public interface OrganizationService {
    Organization getOrganizationByID(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization organization);
    void updateOrganization(Organization organization);
    void deleteOrganizationByID(int id);

    List<Organization> getOrganizationsByHero(Hero hero);
}
