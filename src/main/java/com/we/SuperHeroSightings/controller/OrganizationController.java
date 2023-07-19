package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.OrganizationDao;
import com.we.SuperHeroSightings.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/org")
public class OrganizationController {
    @Autowired
    OrganizationDao organizationDao;

    @GetMapping("/")
    public List<Organization> getOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @PostMapping("/")
    public Organization addOrganization(@RequestBody Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @GetMapping("/{id}")
    public Organization getOrganizationById(@PathVariable("id") int id){
        return organizationDao.getOrganizationByID(id);
    }

    @PutMapping("/{id}")
    public Organization updateOrganizationById(@RequestBody Organization organization){
        organizationDao.updateOrganization(organization);
        return organizationDao.getOrganizationByID(organization.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganizationById(@PathVariable("id") int id){
        organizationDao.deleteOrganizationByID(id);

        if(organizationDao.getOrganizationByID(id) != null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
