
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;

//    It must have a screen(s) to create, view, edit, and delete superhero/supervillain sighting 
//    (superhero/supervillain, location, and time) in the system.

@Controller
//@RequestMapping("/")
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
        
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        
        return "sightings";
    }
    
    @GetMapping("sightingsByLocation")
    public String getSightingByLocation(Integer locationId, Model model, HttpServletRequest request) {  
        Location location = locationService.getLocationById(locationId);
        List<Sighting> sightings = sightingsService.getSightingsByLocation(location);
        model.addAttribute("sightings", sightings);
        
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        
        return "sightings";
    }
    
    @GetMapping("sightingsByDate")
    public String getSightingByDate(Model model, HttpServletRequest request) { 
        
        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("date"));
        
        List<Sighting> sightings = sightingsService.getSightingsByDate(ldt);
        model.addAttribute("sightings", sightings);
        
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        
        return "sightings";
    }
    
    //    It must have a screen(s) to create
    @GetMapping("addSighting")
    public String addSightingPage(Model model, HttpServletRequest request) {        
        List<Hero> heroes = heroService.getAllHeroes();
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        
        return "addSighting";
    }
    
    @PostMapping("addSighting")
    public String addSighting(Model model, HttpServletRequest request) {     
         
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        
        Sighting sighting = new Sighting();
        sighting.setHero(heroService.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));        
        sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        sighting.setDescription(request.getParameter("description"));
        
        sightingsService.addSighting(sighting);
        
        model.addAttribute("localDateTime", LocalDateTime.now());
        
        return "redirect:/sightings";

    }
    
    //    It must have a screen(s) to view
    @GetMapping("editSighting")
    public String getSightingById(Integer id, Model model) {
        
        Sighting sighting = sightingsService.getSightingByID(id);
        
        List<Hero> heroes = heroService.getAllHeroes();
        List<Location> locations = locationService.getAllLocations();
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        
        return "editSighting";
        
    }
    
    @PostMapping("editSighting")
    public String editSighting(Integer id, HttpServletRequest request) {
        Sighting sighting = sightingsService.getSightingByID(id);
        
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        sighting.setHero(heroService.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        
        sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        sighting.setDescription(request.getParameter("description"));        
        
        sightingsService.updateSighting(sighting);
        
        return "redirect:/sightings";
    }
    
    //    It must have a screen(s) to delete
    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingsService.deleteSightingByID(id);
        return "redirect:/sightings";
    } 
    
    @GetMapping("sightingDetails")
    public String courseDetail(Integer id, Model model) {
        Sighting sighting = sightingsService.getSightingByID(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetails";
    }
    
    

}
