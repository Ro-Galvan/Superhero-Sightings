package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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
        //returns view "locations" to display list of locations
        return "locations";
    }

    //method handles HTTP GET requests to "/addLocation"
    @GetMapping("addLocation")
    public String displayAddLocation(Model model) {
        //adds empty location object as an attribute to model for use in view
        model.addAttribute("location", new Location());
        //returns view "addLocation" to display the form for adding new location
        return "addLocation";
    }

    //method handles HTTP POST requests to "/addLocation"
    @PostMapping("addLocation")
    public String addLocation(Location location) {
        //adds new location received from the form to the locationService for storage
        locationService.addLocation(location);
        //redirects user back to locations list after adding location
        return "redirect:/locations";
    }

    //method handles HTTP GET requests to "/editLocation"
    @GetMapping("editLocation")
    public String displayEditLocation(HttpServletRequest request, Model model) {
        //parses 'id' parameter from request URL to identify location to edit
        int id = Integer.parseInt(request.getParameter("id"));
        //gets location with specified id from locationService
        Location location = locationService.getLocationById(id);
        //adds location as an attribute to the model for use in view
        model.addAttribute("location", location);
        //return view "editLocation" to display the form for editing location
        return "editLocation";
    }

    //method handles HTTP POST requests to "/editLocation"
    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request) {
        //parses 'id' parameter from request URL to identify location to edit
        int id = Integer.parseInt(request.getParameter("id"));
        //gets location with specified id from locationService
        Location location = locationService.getLocationById(id);
        //updates location attributes with data received from the form
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));
        //updates location in the locationService
        locationService.updateLocation(location);
        //redirects user back to locations list after editing the location
        return "redirect:/locations/";
    }

    //method handles HTTP GET requests to "/deleteLocation"
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        //parses 'id' parameter from request URL to identify the location to delete
        int id = Integer.parseInt(request.getParameter("id"));
        //deleted the location with the specified id from the locationService
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
