package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.*;
import com.we.SuperHeroSightings.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HeroController {
    @Autowired
    HeroService heroService;
//    @Autowired
//    PowerService powerService;


    @GetMapping("heroes")
    public String displayCourses(Model model) {
        List<Hero> heroes = heroService.getAllHeroes();
//        List<Power> powers = powerService.getAllPowers();
        model.addAttribute("heroes", heroes);
//        model.addAttribute("powers", powers);
        return "heroes";
    }

}
