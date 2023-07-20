package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.PowerDao;
import com.we.SuperHeroSightings.entities.Power;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerServiceImpl implements PowerService{
    private PowerDao powerDao;
    public PowerServiceImpl(PowerDao powerDao){
        this.powerDao = powerDao;
    }
    public Power getPowerById(int id) {
        Power power = powerDao.getPowerByID(id);
        return power;
    }

    public List<Power> getAllPowers() {
        List<Power> powers = powerDao.getAllPowers();
        return powers;
    }

    public Power addPower(Power power) {
        Power newPower = powerDao.addPower(power);
        return newPower;
    }

    public void updatePower(Power power) {
        powerDao.updatePower(power);
    }

    public void deletePowerById(int id) {
        powerDao.deletePowerByID(id);
    }
}
