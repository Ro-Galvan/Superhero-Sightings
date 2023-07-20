package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Power;

import java.util.List;

public interface PowerService {
    Power getPowerById(int id);
    List<Power> getAllPowers();
    Power addPower(Power power);
    void updatePower(Power power);
    void deletePowerById(int id);
}
