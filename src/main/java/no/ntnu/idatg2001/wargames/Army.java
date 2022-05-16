package no.ntnu.idatg2001.wargames;

import no.ntnu.idatg2001.wargames.units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates an army
 *
 * @author Vegard Grøder
 * @version 18.02.2022
 */
public class Army {

    // The class fields
    private String name;
    private List<Unit> unitList;
    private Random randNr;

    /**
     * Initializes the class fields
     *
     * @param name The name of the army
     */
    public Army(String name) {
        this.name = name;
        this.unitList = new ArrayList<>();
        this.randNr = new Random();
    }

    /**
     * Initializes the class fields
     *
     * @param name  The name of the army
     * @param unitList A list containing unit objects
     */
    public Army(String name, List<Unit> unitList) {
        this.name = name;
        this.unitList = unitList;
        this.randNr = new Random();
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
     * Returns the army list
     *
     * @return name The name of the army
     */
    public List<Unit> getUnitList() {
        return this.unitList;
    }

    /**
     * Returns a list of all the infantry units in one army
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
     * @return boolean True if the army is not empty, false if the army is empty
     */
    public boolean hasUnit() {
        boolean listNotEmpty = true;
        if (this.unitList.isEmpty()) {
            return !listNotEmpty;
        }
        return listNotEmpty;
    }

    /**
     * Searches for a random unit in the army
     *
     * @return Unit A random unit object
     */
    public Unit getRandom() {
        int upperbound = this.unitList.size();
        int randomNumber = this.randNr.nextInt(upperbound);
        return this.unitList.get(randomNumber);
    }

    public int getRandomNumber() {
        int upperbound = this.unitList.size();
        return this.randNr.nextInt(upperbound);
    }
}
