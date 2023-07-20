package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.*;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.OrganizationService;
import com.we.SuperHeroSightings.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HeroController {
    @Autowired
    HeroService heroService;
    @Autowired
    PowerService powerService;
    @Autowired
    OrganizationService orgService;


    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroService.getAllHeroes();
        List<Power> powers = powerService.getAllPowers();
        model.addAttribute("heroes", heroes);
        model.addAttribute("powers", powers);
        return "heroes";
    }

//    @PostMapping("addHero")
//    public String addHero(Hero hero, HttpServletRequest request) {
////        String teacherId = request.getParameter("type");
//        String powerId = request.getParameter("power");
//        String[] organizationIds = request.getParameterValues("organizationId");
//
////        hero.setType(heroService.); TODO might need to add another method to service for getting the type
//        hero.setPower(powerService.getPowerById(Integer.parseInt(powerId)));
//
//
//        List<Organization> organizationArrayList = new ArrayList<>();
//        for(String orgId : organizationIds) {
//            organizationArrayList.add(orgService.getOrganizationByID(Integer.parseInt(orgId)));
//        }
//        hero.setOrganizations(organizationArrayList);
//        heroService.addHero(hero);
//
//        return "redirect:/heroes";
//    }



//              **************ADD HERO*************
    @GetMapping("addHero")
    public String displayAddHero(Model model) {
        //adds empty hero object as an attribute to model for use in view
        model.addAttribute("hero", new Hero());
        //returns view "addHero" to display the form for adding new hero
        return "addHero";

//        List<Organization> organizations = heroService.get;

    }

    //method handles HTTP POST requests to "/addLocation"
    @PostMapping("addHero")
    public String addHero(Hero hero) {
        //adds new location received from the form to the locationService for storage
        heroService.addHero(hero);
        //redirects user back to locations list after adding location
        return "redirect:/heroes";
    }

//    @PostMapping("addHero")
//    public String addHero(HttpServletRequest request, Model model) {
//        Hero hero = new Hero();
//        hero.setName(name);
//        hero.setType(type);
//        hero.setDescription(description);
//
//        heroService.addHero(hero);
//
//        return "redirect:/heroes";
////        String name = request.getParameter("name");
////        String type = request.getParameter("type");
////        String description = request.getParameter("description");
//
////                List<Organization> organizationArrayList = new ArrayList<>();
////        for(String orgId : organizationIds) {
////            organizationArrayList.add(orgService.getOrganizationByID(Integer.parseInt(orgId)));
////        }
//    }

    /**
     * We take in the ID from the URL and a Model so we can send data to the page.
     * We use the ID to get the Hero and add it to the Model.
     * Finally, we return courseDetail, which will send us to the heroDetail.html page.
     *
     */
    @GetMapping("detailHero")
    public String detailHero(Integer id, Model model) {
        Hero hero = heroService.getHeroByID(id);
        model.addAttribute("hero", hero);
        return "detailHero";
    }

}
