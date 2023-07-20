package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LocationController {
    //autowires Location and Hero Service for dependency injection
    @Autowired
    LocationService locationService;

    @Autowired
    HeroService heroService;

    //method handles HTTP GET requests to "/locations"
    @GetMapping("locations")
    public String displayLocations(Model model) {
        //gets all locations from locationService
        List<Location> locations = locationService.getAllLocations();
        //adds list of locations as an attribute to model for use in view
        model.addAttribute("locations", locations);
        return "locations";
    }

    //method handles HTTP GET requests to "/addLocation"
    @GetMapping("addLocation")
    public String displayAddLocation(Model model) {
        Location location = new Location();
        //adds list of locations as an attribute to model for use in view
        model.addAttribute("location", location);
        return "addLocation";
    }

    //method handles HTTP POST requests to "/addLocation"
    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result) {
        //@Valid annotation is used to trigger validation on Location object
        //BindingResult contains results of validation including any errors occurred

        //if there are errors, return to "addLocation" view to display errors and correct the form
        if(result.hasErrors()) {
            return "addLocation";
        }

        //if there are no errors, add location to locationService and redirect to "/locations"
        locationService.addLocation(location);
        return "redirect:/locations";
    }

    //method handles HTTP GET requests to "/editLocation"
    @GetMapping("editLocation")
    public String displayEditLocation(Integer id, Model model) {
        //retrieves location with specified id from locationService
        Location location = locationService.getLocationById(id);
        //adds location as attribute to model for use in view
        model.addAttribute("location", location);
        //returns view "editLocation" to display the form for editing the location
        return "editLocation";
    }

    //method handles HTTP POST requests to "/editLocation"
    @PostMapping("editLocation")
    public String editLocation(@Valid Location location, BindingResult result) {
        //@Valid annotation is used to trigger validation on Location object
        //BindingResult contains results of validation including any errors occurred

        //if there are errors, return to "editLocation" view to display errors and correct the form
        if(result.hasErrors()) {
            return "editLocation";
        }

        //if there are no errors, update location to locationService and redirect to "/locations"
        locationService.updateLocation(location);
        return "redirect:/locations";
    }

    //method handles HTTP GET requests to "/deleteLocation"
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        //deletes the location with the specified id from the locationService
        locationService.deleteLocationById(id);
        //redirects user back to locations list after deleting the location
        return "redirect:/locations";
    }

    // method handles HTTP GET requests to "/locationDetail"
    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        //gets location with specified id from locationService
        Location location = locationService.getLocationById(id);
        //gets list of heroes associated with the location from heroService
        List<Hero> heroesByLocation = heroService.getHeroesByLocation(location);
        //adds location and heroes list as attributes to model for use in view
        model.addAttribute("location", location);
        model.addAttribute("heroes", heroesByLocation);
        //returns view "locationDetail" to display details of location and associated heroes
        return "locationDetail";
    }

}
