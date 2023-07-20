package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LocationController {
    //autowires Location and Hero Service for dependency injection
    @Autowired
    LocationService locationService;

    @Autowired
    HeroService heroService;

    //sets to store ConstraintViolation objects for validation errors
    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    //method handles HTTP GET requests to "/locations"
    @GetMapping("locations")
    public String displayLocations(Model model) {
        //gets all locations from locationService
        List<Location> locations = locationService.getAllLocations();
        //adds list of locations as an attribute to model for use in view
        model.addAttribute("locations", locations);
        //initializes violations as empty HashSet
        violations = new HashSet<>();
        //returns view "locations" to display list of locations
        return "locations";
    }

    //method handles HTTP GET requests to "/addLocation"
    @GetMapping("addLocation")
    public String displayAddLocation(Model model) {
        //adds empty location object as an attribute to model for use in view
        model.addAttribute("location", new Location());
        //adds violations set as an attribute to model for displaying validation errors in view
        model.addAttribute("errors", violations);
        //returns view "addLocation" to display the form for adding new location
        return "addLocation";
    }

    //method handles HTTP POST requests to "/addLocation"
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request, Model model) {
        //creates new location object and sets its attributes from data received from the form
        Location location = new Location();
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));

        //adds newly created location object as an attribute to model for use in view
        model.addAttribute("location", location);

        //validates location object using Bean Validation API and stores ay constraint violations in violations set
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        //if there are no validation errors, adds location to service and redirects user to "/locations"
        if(violations.isEmpty()) {
            locationService.addLocation(location);
            return "redirect:/locations";
        }

        //if there are errors, redirects user back to "/addLocation" to display errors and correct the form
        return "redirect:/addLocation";
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
        //adds violations set as an attribute to model for displaying validation errors in view
        model.addAttribute("errors", violations);
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

        //validates location object using Bean Validation API and stores ay constraint violations in violations set
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        //if there are no validation errors, adds location to service and redirects user to "/locations"
        if(violations.isEmpty()) {
            locationService.updateLocation(location);
            return "redirect:/locations";
        }

        //if there are errors, redirects user back to "/editLocation" to display errors and correct the form
        return "redirect:/editLocation?id="+id;
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
