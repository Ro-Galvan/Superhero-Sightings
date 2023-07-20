package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Power;
import com.we.SuperHeroSightings.entities.Sighting;
import com.we.SuperHeroSightings.service.PowerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class PowerController {
    @Autowired
    private PowerServiceImpl powerService;

    public PowerController(PowerServiceImpl powerService) {
        this.powerService = powerService;
    }

    //-We want to be able to go to the Powers html page
    @GetMapping("powers")
    public String displayPowers(Model model) {
        // Display all powers.
        // Retrieve the list of powers from the PowerService
        List<Power> powers = powerService.getAllPowers();

        // Add the list of powers to the model to be used in the view (HTML template)
        model.addAttribute("powers", powers);

        // Return the name of the HTML template to be rendered
        // In this case, it will look for a file named "powers.html"
        return "powers";
    }


    @GetMapping("addPower")
    public String displayAddPower(Model model) {
        model.addAttribute("powers", new Power());
        return "addPower";
    }

    @PostMapping("addPower")
    public String addPower(@Valid Power power) {
      powerService.addPower(power);
        return "redirect:/powers";
    }

    @GetMapping("detailPower")
    public String detailPower(Integer id, Model model) {
        // Get the Power object with the given ID from the database
        Power power = powerService.getPowerById(id);

        // Add the retrieved Power object to the model to be displayed in the view
        model.addAttribute("power", power);

        // Return the "detailPower" template to be rendered
        return "detailPower";
    }


    // Handling GET request for "/editPower" URL
    @GetMapping("editPower")
    public String editPower(Integer id, Model model) {
        //-Get the Power object with the given ID from the database
        Power power = powerService.getPowerById(id);

        //-Add the retrieved Power object to the model to be used in the form
        model.addAttribute("power", power);

        //-Return the "editPower" template to be rendered
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result) {
        // Check if there are validation errors in the form submission
        if(result.hasErrors()) {
            // If there are errors, return to the "editPower" page to show the errors
            return "editPower";
        }

        // Update the Power object received from the form in the database
        powerService.updatePower(power);
        // Redirect to the "powers" page after updating the power
        return "redirect:/powers";
    }

    // Handling GET request for "/deletePower" URL
    @GetMapping("deletePower")
    public String deletePower(Integer id) {
        // Delete the Power object with the given ID from the database
        powerService.deletePowerById(id);

        // Redirect to the "powers" page after deleting the power
        return "redirect:/powers";
    }
}
