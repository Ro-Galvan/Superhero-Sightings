package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.OrganizationDao;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/org")
public class OrganizationController {
    @Autowired
    OrganizationService service;

    @GetMapping("")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organization";
    }

}
