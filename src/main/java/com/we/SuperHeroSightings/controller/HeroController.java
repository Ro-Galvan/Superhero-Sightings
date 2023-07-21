package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.*;
import com.we.SuperHeroSightings.service.*;
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

    @Autowired
    SightingService serviceService;


    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroService.getAllHeroes();
        List<Power> powers = powerService.getAllPowers();
        model.addAttribute("heroes", heroes);
        model.addAttribute("powers", powers);
        return "heroes";
    }


//              **************ADD HERO*************
    @GetMapping("addHero")
    public String getAddHeroPage(Model model) {
//        getting a list of all available powers & organizations
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = orgService.getAllOrganizations();
        //adds empty hero object / List of org & powers as an attribute to model to display to web
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("hero", new Hero());
        //returns view "addHero" to display the form for adding new hero
        return "addHero";

    }

    @PostMapping("addHero")
    public String addHero(HttpServletRequest request, Model model) {
//         pull out the teacherId data from the HttpServletRequest
        String powerIDs = request.getParameter("powerID");
//        use the getParameterValues method to get a string array of organizationIDs
        String[] organizationIDs = request.getParameterValues("organizationID");

//        take in a Hero object that captures the name, type, description fields and an HttpServletRequest object that we use to capture those fields
        Hero hero = new Hero();
        hero.setName(request.getParameter("name"));
        hero.setType(request.getParameter("type"));
        hero.setDescription(request.getParameter("description"));
        heroService.addHero(hero);
//      setting power but using Power Service
        hero.setPower(powerService.getPowerById(Integer.parseInt(powerIDs)));

//         create an empty list of organizations, loop through the organizationIds, retrieve each organization, and add it to the list.
        List<Organization> organizationArrayList = new ArrayList<>();
        for(String organizationID : organizationIDs) {
            organizationArrayList.add(orgService.getOrganizationByID(Integer.parseInt(organizationID)));
        }
        hero.setOrganizations(organizationArrayList);


        return "redirect:/heroes";
    }

//    ************BACK UP ONE IN CASE I CAN"T GET THE OTHER ONE WORKING******************
//    @GetMapping("addHero")
//    public String displayAddHero(Model model) {
//        //adds empty hero object as an attribute to model for use in view
//        model.addAttribute("hero", new Hero());
//        //returns view "addHero" to display the form for adding new hero
//        return "addHero";
//    }

    //method handles HTTP POST requests to "/addLocation"
//    @PostMapping("addHero")
//    public String addHero(Hero hero) {
//        //adds new location received from the form to the locationService for storage
//        heroService.addHero(hero);
//        //redirects user back to locations list after adding location
//        return "redirect:/heroes";
//    }
//    ************BACK UP ONE IN CASE I CAN"T GET THE OTHER ONE WORKING******************


    //              **************EDIT HERO*************
    @GetMapping("editHero")
    public String getAddHeroEditPage(Model model) {
//        getting a list of all available powers & organizations
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = orgService.getAllOrganizations();
        //adds empty hero object / List of org & powers as an attribute to model to display to web
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("hero", new Hero());
        //returns view "editHero" to display the form for editing new hero
        return "editHero";

    }

    @PostMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        String powerIDs = request.getParameter("powerID");
        String[] organizationIDs = request.getParameterValues("organizationID");

        Hero hero = new Hero();
        hero.setName(request.getParameter("name"));
        hero.setType(request.getParameter("type"));
        hero.setDescription(request.getParameter("description"));
        heroService.addHero(hero);

        hero.setPower(powerService.getPowerById(Integer.parseInt(powerIDs)));

        List<Organization> organizationArrayList = new ArrayList<>();
        for(String organizationID : organizationIDs) {
            organizationArrayList.add(orgService.getOrganizationByID(Integer.parseInt(organizationID)));
        }
        hero.setOrganizations(organizationArrayList);

        return "redirect:/heroes";
    }


    /**
     * handles HTTP GET requests for the URL path /detailHero
     * method retrieves info about a superhero & adds this data to the Model.
     * The Thymeleaf template, "detailHero.html," will use this data to render the details of the superhero on the web page.
     * @param id  identifies the specific hero for which the details will be fetched
     * @param model Spring Model object that allows you to pass data from the controller to the view (Thymeleaf template) for rendering.
     * @return method returns the String "detailHero". This indicates that the Thymeleaf template named "detailHero.html" will be used to render the response for the request.
     */
    @GetMapping("detailHero")
    public String detailHero(Integer id, Model model) {
//        calls the method from Service (pass through method from DAO) that retrieves the Hero object with the selected id and assigns it to hero variable
        Hero hero = heroService.getHeroByID(id);
//        retrieve a list of Organization objects to which the superhero belongs. The method is passed the Hero object, hero, from above
        List<Organization> getHeroesByOrganization = orgService.getOrganizationsByHero(hero);
//        retrieve a list of Sighting objects associated with the superhero. This method is also passed the Hero object, hero variable
        List<Sighting> sightingsByHero = serviceService.getSightingsByHero(hero);

//        adds the Hero object to the Model with the attribute name "hero". This will make the Hero object accessible in the Thymeleaf template.
        model.addAttribute("hero", hero);
//       adds the list of Organization objects to the Model with the attribute name "organizations". This will make the list of organizations accessible in the Thymeleaf template.
        model.addAttribute("organizations", getHeroesByOrganization);
//        adds the list of Sighting objects to the Model with the attribute name "sightings". This will make the list of sightings accessible in the Thymeleaf template.
        model.addAttribute("sightings", sightingsByHero);
        return "detailHero";
    }

    /**
     *
     * Using the ID, we call deleteHeroByID in the Hero Service.
     * Finally, we redirect back to the main Hero page.
     * @param id take in the ID from the URL
     * @return
     */
    @GetMapping("deleteHero")
    public String deleteLocation(Integer id) {
        //deletes the hero with the specified id from Service
        heroService.deleteHeroByID(id);
        //redirects user back to hero page after deleting the hero
        return "redirect:/heroes";
    }

}
