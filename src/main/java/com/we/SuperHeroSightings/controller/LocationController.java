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
    @Autowired
    LocationService locationService;

    @Autowired
    HeroService heroService;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @GetMapping("addLocation")
    public String displayAddLocation(Model model) {
        model.addAttribute("location", new Location());
        return "addLocation";
    }

    @PostMapping("addLocation")
    public String addLocation(Location location) {
        locationService.addLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String displayEditLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationService.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationService.getLocationById(id);
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));
        return "redirect:/locations/";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationService.deleteLocationById(id);
        return "redirect:/locations";
    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = locationService.getLocationById(id);
        List<Hero> heroesByLocation = heroService.getHeroesByLocation(location);
        model.addAttribute("location", location);
        model.addAttribute("heroes", heroesByLocation);
        return "locationDetail";
    }

}
