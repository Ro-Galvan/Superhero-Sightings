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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrganizationController {
    @Autowired
    OrganizationService service;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organization";
    }

    @GetMapping("organizations/add")
    public String displayAddOrganization(Model model) {
        return "addOrganization";
    }

    @PostMapping("/organizations/add")
    public String addOrganization(HttpServletRequest request){
        Organization org = new Organization();
        org.setName(request.getParameter("name"));
        org.setType(request.getParameter("type"));
        org.setDescription(request.getParameter("description"));
        org.setAddress(request.getParameter("address"));
        org.setPhone(request.getParameter("phone"));
        org.setContact(request.getParameter("contact"));
        service.addOrganization(org);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteOrganizationByID(id);

        return "redirect:/organizations";
    }

}
