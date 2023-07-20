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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrganizationController {
    @Autowired
    OrganizationService service;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    Organization addedObj;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        violations = new HashSet<>();
        addedObj = new Organization();
        return "organization";
    }

    @GetMapping("/organizations/add")
    public String displayAddOrganization(Model model) {
        model.addAttribute("errors", violations);
        model.addAttribute("addedObj", addedObj);
        return "addOrganization";
    }

    @PostMapping("/organizations/add")
    public String addOrganization(HttpServletRequest request, Model model){
        Organization org = new Organization();
        org.setName(request.getParameter("name"));
        org.setType(request.getParameter("type"));
        org.setDescription(request.getParameter("description"));
        org.setAddress(request.getParameter("address"));
        org.setPhone(request.getParameter("phone"));
        org.setContact(request.getParameter("contact"));
        addedObj = org;
        model.addAttribute("addedObj", addedObj);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if(violations.isEmpty()){
            service.addOrganization(org);
            return "redirect:/organizations";
        }

        return "redirect:/organizations/add";
    }

    @GetMapping("/organizations/edit")
    public String displayEditOrganization(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = service.getOrganizationByID(id);
        System.out.print("---------");
        System.out.print(organization);
        System.out.print("---------");
        model.addAttribute("organization", organization);
        return "editOrganization";
    }

    @PostMapping("/organizations/edit")
    public String editOrganization(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = service.getOrganizationByID(id);
        org.setName(request.getParameter("name"));
        org.setType(request.getParameter("type"));
        org.setDescription(request.getParameter("description"));
        org.setAddress(request.getParameter("address"));
        org.setPhone(request.getParameter("phone"));
        org.setContact(request.getParameter("contact"));

        service.updateOrganization(org);

        return "redirect:/organizations";
    }

    @GetMapping("/organizations/details")
    public String displayOrganizationDetails(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = service.getOrganizationByID(id);

        model.addAttribute("organization", organization);
        return "detailOrganization";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteOrganizationByID(id);

        return "redirect:/organizations";
    }

}
