package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Power;
import com.we.SuperHeroSightings.service.PowerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@RestController
//@RequestMapping("/")
@Controller
public class PowerController {
    @Autowired
    private PowerServiceImpl powerService;

    public PowerController(PowerServiceImpl powerService) {
        this.powerService = powerService;
    }
    Set<ConstraintViolation<Power>> violations = new HashSet<>();

    //-We want to be able to go to the Powers html page
    @GetMapping("/powers")
    public String displayPowers(Model model) {
        // Display all powers.
        // Retrieve the list of powers from the PowerService
        List<Power> powers = powerService.getAllPowers();

        // Add the list of powers to the model to be used in the view (HTML template)
        model.addAttribute("powers", powers);

        // Initialize a new Power object for the form in case the view needs it
        //model.addAttribute("power", new Power()); NOT SURE IF I NEED THIS

        // Return the name of the HTML template to be rendered
        // In this case, it will look for a file named "powers.html"
        return "powers";
    }

    @PostMapping("/addPower")
    public String addPower(@Valid Power power, BindingResult result, Model model) {
        //-This will check if the BindingResult has any errors.
        //-If it does, it returns immediately to the same page we were coming from
        if(result.hasErrors()) {
            List<Power> powers = powerService.getAllPowers();
            model.addAttribute("powers", powers);
            return displayPowers(model);
        }

        powerService.addPower(power);
        return "redirect:/powers";
    }
}
