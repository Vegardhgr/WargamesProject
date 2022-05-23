package no.ntnu.idatg2001.wargames.core;

import no.ntnu.idatg2001.wargames.core.units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates an army
 *
 * @author Vegard Grøder
 * @version 1.0.0
 */
public class Army {

    // The class fields and objects
    private String name;
    private List<Unit> unitList;
    private Random randNr;

    /**
     * Initializes the class fields and objects
     *
     * @param name The name of the army
     */
    public Army(String name) {
        this.name = name;
        this.unitList = new ArrayList<>();
        this.randNr = new Random();
    }

    /**
     * Initializes the class fields and objects
     *
     * @param name     The name of the army
     * @param unitList A list containing unit objects
     */
    public Army(String name, List<Unit> unitList) {
        this.name = name;
        this.unitList = unitList;
        this.randNr = new Random();
    }

    /**
     * Copy constructor that takes an army as a parameter and clones
     * this army
     * @param army, the army that should be cloned
     */
    public Army(Army army) {
        this.randNr = new Random();
        this.name = army.getName();
        this.unitList = new ArrayList<>();
        for (Unit unit : army.getUnitList()) {
            unitList.add(UnitFactory.getInstance()
                    .createOneUnit(UnitFactory.UnitType.valueOf(
                            unit.getClass().getSimpleName().toUpperCase()), unit.getName(), unit.getHealth()));
        }
    }

    /**
     * Returns the army name
     *
     * @return name The name of the army
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the unit list
     *
     * @return name The name of the army
     */
    public List<Unit> getUnitList() {
        return this.unitList;
    }

    /**
     * Returns a list of all the infantry units in one army
     *
     * @return List<Unit>, a list of infantry units
     */
    public List<Unit> getInfantryUnit() {
        return this.unitList
                .stream()
                .filter(unit -> unit.getClass() == InfantryUnit.class)
                .toList();
    }

    /**
     * Returns a list of all the cavalry units in one army
     *
     * @return List<Unit>, a list of cavalry units
     */
    public List<Unit> getCavalryUnit() {
        return this.unitList
                .stream()
                .filter(unit -> unit.getClass() == CavalryUnit.class)
                .toList();
    }

    /**
     * Returns a list of all the commander units in one army
     *
     * @return List<Unit>, a list of commander units
     */
    public List<Unit> getCommanderUnitList() {
        return this.unitList
                .stream()
                .filter(unit -> unit.getClass() == CommanderUnit.class)
                .toList();
    }

    /**
     * Returns a list of all the ranged units in one army
     *
     * @return List<Unit>, a list of ranged units
     */
    public List<Unit> getRangedUnitList() {
        return this.unitList
                .stream()
                .filter(unit -> unit.getClass() == RangedUnit.class)
                .toList();
    }

    /**
     * Adds a unit to the army
     *
     * @param unit A unit object
     */
    public void add(Unit unit) {
        this.unitList.add(unit);
    }

    /**
     * Adds unit objects to the army
     *
     * @param units A list of unit objects
     */
    public void addAll(List<Unit> units) {
        this.unitList.addAll(units);
    }

    /**
     * Removes a unit from the army
     *
     * @param unit A unit object
     */
    public void remove(Unit unit) {
        this.unitList.remove(unit);
    }

    /**
     * Checks if the army contains one or more units
     *
     * @return boolean, True if the army is not empty, false if the army is empty
     */
    public boolean hasUnit() {
        boolean listNotEmpty = true;
        if (this.unitList.isEmpty()) {
            return !listNotEmpty;
        }
        return listNotEmpty;
    }

    /**
     * Returns a random unit in the army
     *
     * @return Unit, a random unit object
     */
    public Unit getRandom() {
        int upperbound = this.unitList.size();
        int randomNumber = this.randNr.nextInt(upperbound);
        return this.unitList.get(randomNumber);
    }

    /**
     * Returns a random number based on the army size
     *
     * @return int, the number of units in the army
     */
    public int getRandomNumber() {
        int upperbound = this.unitList.size();
        return this.randNr.nextInt(upperbound);
    }


    /**
     * Compares two armies
     *
     * @param comparableArmy, the army to be compared with
     * @return boolean, true if the two armies have equal values
     */
    @Override
    public boolean equals(Object comparableArmy) {
        if (comparableArmy instanceof Army army2 && this.getName().equals(army2.getName())) {
            for (int i = 0; i < this.getUnitList().size(); i++) {
                if (!((this.getUnitList().get(i).getName().equals(army2.getUnitList().get(i).getName())) &&
                        (this.getUnitList().get(i).getHealth() == (army2.getUnitList().get(i).getHealth())) &&
                        (this.getUnitList().get(i).getAttack() == (army2.getUnitList().get(i).getAttack())) &&
                        (this.getUnitList().get(i).getArmor() == (army2.getUnitList().get(i).getArmor())))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns an int of the total army health
     *
     * @return int, total army health
     */
    public long getTotalHealth() {
        long totalHealth = 0;
        for (Unit unit : this.unitList) {
            totalHealth += unit.getHealth();
        }
        return totalHealth;
    }
}
