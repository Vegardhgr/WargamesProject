package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.units.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates an army
 *
 * @author  Vegard Grøder
 * @version 18.02.2022
 */
public class Army {

    // The class fields
    private String name;
    private List<Unit> units;

    /**
     * Initializes the class fields
     *
     * @param name The name of the army
     */
    public Army(String name) {
        this.name = name;
        this.units = new ArrayList<>();
    }

    /**
     * Initializes the class fields
     *
     * @param name The name of the army
     * @param units A list containing unit objects
     */
    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
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
     * Adds a unit to the army
     *
     * @param unit A unit object
     */
    public void add(Unit unit) {
        units.add(unit);
    }

    /**
     * Adds unit objects to the army
     *
     * @param units A list of unit objects
     */
    public void addAll(List<Unit> units) {
        for (Unit unit : units) {
            this.units.add(unit);
        }
    }

    /**
     * Removes a unit from the army
     *
     * @param unit A unit object
     */
    public void remove(Unit unit) {
        units.remove(unit);
    }

    /**
     * Checks if the army contains one or more units
     *
     * @return boolean True if the army is not empty, false if the army is empty
     */
    public boolean hasUnit() {
        boolean listNotEmpty = true;
        if (units.isEmpty()) {
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
        Random randNr = new Random();
        int upperbound = units.size();
        int randomNumber = randNr.nextInt(upperbound);
        return units.get(randomNumber);
    }
}
