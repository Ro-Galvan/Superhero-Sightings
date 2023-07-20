package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.OrganizationDao;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationDao organizationDao;

    @Override
    public Organization getOrganizationByID(int id) {
        Organization org = organizationDao.getOrganizationByID(id);
        if (org == null){
            throw new RuntimeException("Could not find an organization with id " +id+".");
        }
        return org;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        Organization org = organizationDao.getOrganizationByID(organization.getId());
        if (org == null){
            throw new RuntimeException("Could not find an organization with id " +organization.getId()+".");
        }
        organizationDao.updateOrganization(organization);
    }

    @Override
    public void deleteOrganizationByID(int id) {
        organizationDao.deleteOrganizationByID(id);
    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        return organizationDao.getOrganizationsByHero(hero);
    }
}
