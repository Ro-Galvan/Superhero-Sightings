
package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.SightingService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.we.SuperHeroSightings.service.LocationService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    
    /*This Validator will look at the annotations we have added to the entity and check if the field matches them. 
    Any data that does not match is added as an error to a list it gives back to us. */
    
    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    
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
        model.addAttribute("errors", violations);
        
        return "addSighting";
    }
    
    @PostMapping("addSighting")
    public String addSighting(Model model, HttpServletRequest request) {     
        Sighting sighting = new Sighting();
        sighting.setDescription(request.getParameter("description"));        

        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");        

        sighting.setHero(heroService.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        
        if(!request.getParameter("date").equals("")){
            sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        }else{
            return "redirect:/addSighting";
        }
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()) {                 
            sightingsService.addSighting(sighting);            
            return "redirect:/sightings";
        }
                
        return "redirect:/addSighting";

    }
    
    //    It must have a screen(s) to view
    @GetMapping("editSighting")
    public String getEditSightingById(Integer id, Model model) {
        Sighting sighting;
        if(id != null){
            sighting = sightingsService.getSightingByID(id);
        }else{
            return "sighting";
        }
        
        List<Hero> heroes = heroService.getAllHeroes();
        List<Location> locations = locationService.getAllLocations();
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        
        return "editSighting";
        
    }
    
    @PostMapping("editSighting")
    public String editSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        
        if (result.hasErrors()) {
            List<Hero> heroes = heroService.getAllHeroes();
            model.addAttribute("heroes", heroes);

            List<Location> locations = locationService.getAllLocations();
            model.addAttribute("locations", locations);
                        
            return "editSighting";
        }
                
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId")); 

        sighting.setHero(heroService.getHeroByID(heroId));
        sighting.setLocation(locationService.getLocationById(locationId));
        sighting.setDate(LocalDateTime.parse(request.getParameter("date")));

        sightingsService.updateSighting(sighting);

        return "redirect:/sightings";
    }
    
    static String getTime(String time)
    {
        String format;
 
        // Parsing hours, minutes and seconds in array
        String[] arr = time.split(" ");
        String hhmm = arr[1];
        String[] arrHHMM = hhmm.split(":");
        int hh = Integer.parseInt(arr[0]);
 
        // Converting hours into integer
 
        if (hh > 12) {
            format = "PM";
        }
        else if (hh == 00) {
            format = "AM";
        }
        else if (hh == 12) {
            format = "PM";
        }
        else {
            format = "AM";
        }

        return format;
    }
    
    /*@PostMapping("editSighting")
    public String editSighting(@Valid Sighting sighting, Integer id, BindingResult result, HttpServletRequest request, Model model) {
//        try{
//            
//        }
//        catch(DataAccessException ex){
//            
//        }        
        
        if(id != null){
            sighting = sightingsService.getSightingByID(id);
            String heroId = request.getParameter("heroId");
            String locationId = request.getParameter("locationId");
            sighting.setHero(heroService.getHeroByID(Integer.parseInt(heroId)));
            sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));                             
        } 
        if(!request.getParameter("date").equals("")){
            sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        }
        else{
            return "redirect:/editSighting";
        }
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()) {
            sighting.setDescription(request.getParameter("description")); 
            sightingsService.updateSighting(sighting);
            return "redirect:/editSightings";
        }
        
        if(result.hasErrors()) {
            return "editSightings";
        }     

//        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("date"), DateTimeFormatter.BASIC_ISO_DATE);
//        sighting.setDate(ldt);       
        //sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        //sightingsService.updateSighting(sighting);
        
        return "redirect:/sightings";
    }*/
    
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

