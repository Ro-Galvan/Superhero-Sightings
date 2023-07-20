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
    @GetMapping("powers")
    public String displayPowers(Model model) {
        // Display all powers.
        // Retrieve the list of powers from the PowerService
        List<Power> powers = powerService.getAllPowers();

        // Add the list of powers to the model to be used in the view (HTML template)
        model.addAttribute("powers", powers);

        // Initialize a new Power object for the form in case the view needs it
        model.addAttribute("power", new Power()); // NOT SURE IF I NEED THIS

        // Return the name of the HTML template to be rendered
        // In this case, it will look for a file named "powers.html"
        return "powers";
    }

//    @PostMapping("/addPower")
//    public String addPower(@Valid Power power, BindingResult result, Model model) {
//        //-This will check if the BindingResult has any errors.
//        //-If it does, it returns immediately to the same page we were coming from
//        if(result.hasErrors()) {
//            List<Power> powers = powerService.getAllPowers();
//            model.addAttribute("powers", powers);
//            return displayPowers(model);
//        }
//
//        powerService.addPower(power);
//        return "redirect:/powers";
//    }

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        String powerName = request.getParameter("power");
        String description = request.getParameter("description");


        Power power = new Power();
        power.setName(powerName);
        power.setName(description);


        powerService.addPower(power);
        return "redirect:/powers";
    }

    @GetMapping("detailPower")
    public String detailPower(Integer id, Model model) {
        Power power = powerService.getPowerById(id);
        model.addAttribute("power", power);
        return "detailPower";
    }

    @GetMapping("editPower")
    public String editPower(Integer id, Model model) {
        Power power = powerService.getPowerById(id);
        model.addAttribute("power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "editPower";
        }

        powerService.updatePower(power);
        return "redirect:/detailPower?id=" + power.getId();
    }

    @GetMapping("deletePower")
    public String deletePower(Integer id) {
        powerService.deletePowerById(id);
        return "redirect:/powers";
    }
}
