
package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.SightingService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.we.SuperHeroSightings.service.LocationService;
import org.springframework.stereotype.Controller;

//    It must have a screen(s) to create, view, edit, and delete superhero/supervillain sighting 
//    (superhero/supervillain, location, and time) in the system.

@Controller
//@RequestMapping("sightings")
public class SightingsController {

    @Autowired
    SightingService sightingsService;
    
    @Autowired
    HeroService heroService;
    
    @Autowired
    LocationService locationService;
    
    
    @GetMapping("sightings")
    public String getAllSighting(Model model) {  
        List<Sighting> sightings = sightingsService.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "sightings";
    }
    
    //    It must have a screen(s) to create
    @PostMapping("addSighting")
    public String addSighting(@RequestBody Sighting sighting, HttpServletRequest request) {
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        
        sighting.setHero(heroService.getHeroByID(Integer.parseInt(heroId)));

        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        sightingsService.addSighting(sighting);
        
        return "redirect:/sightings";

    }
    
    //    It must have a screen(s) to view
    @GetMapping("editSighting")
    public String getSightingById(Integer id, Model model) {
        
        Sighting sighting = sightingsService.getSightingByID(id);
        
        List<Hero> heros = heroService.getAllHeroes();
        List<Location> locations = locationService.getAllLocations();
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);
        return "editSighting";
        
    }
    @PostMapping("editSighting")
    public String performEditCourse(Sighting sighting, HttpServletRequest request) {
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        
        sighting.setHero(heroService.getHeroByID(Integer.parseInt(heroId)));

        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        sightingsService.addSighting(sighting);
        
        return "redirect:/sightings";
    }
    
    //    It must have a screen(s) to delete
    @DeleteMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingsService.deleteSightingByID(id);
        return "redirect:/sightings";
    } 
    
    @GetMapping("SightingDetails")
    public String courseDetail(Integer id, Model model) {
        Sighting sighting = sightingsService.getSightingByID(id);
        model.addAttribute("sighting", sighting);
        return "SightingDetail";
    }

}
