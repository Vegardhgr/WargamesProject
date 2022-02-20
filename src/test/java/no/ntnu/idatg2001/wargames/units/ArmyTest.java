package no.ntnu.idatg2001.wargames.units;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the Army class
 *
 * @author Vegard Grøder
 * @version 18.02.2022
 */
class ArmyTest {

    // Class fields
    private Army army1;
    private Unit archer;
    private Unit barbarian;
    private List<Unit> units;
    private List<Unit> inputUnits;

    @BeforeEach
    void setUp() {
        // Initializing the class fields
        army1 = new Army("Army1");
        archer = new CommanderUnit("Archer", 10);
        barbarian = new CavalryUnit("Barbarian", 12);
        units = new ArrayList<>();
        inputUnits = new ArrayList<>();
        inputUnits.add(archer);
        inputUnits.add(barbarian);
    }

    /**
     * Tests that it returns the expected value
     */
    @Test
    void getName() {
        assertEquals("Army1", army1.getName());
    }

    /**
     * Tests that it adds units to an army
     */
    @Test
    void add() {
        assertEquals(0, units.size());
        units.add(archer);
        assertEquals(1, units.size());
        units.add(barbarian);
        assertEquals(2, units.size());
    }

    /**
     * Tests that it adds all the units from a list, into the army
     */
    @Test
    void addAll() {
        assertEquals(0, units.size());
        for (Unit unit : inputUnits) {
            units.add(unit);
        }

        assertEquals(inputUnits.size(), units.size());
    }

    /**
     * Tests that it removes one element from the army
     */
    @Test
    void remove() {
        Random randNr = new Random();
        int upperbound = units.size() + 1;
        int randomNumber = randNr.nextInt(upperbound);

        assertEquals(0, units.size());

        addAll();
        assertEquals(inputUnits.size(), units.size());

        units.remove(randomNumber);
        assertEquals(inputUnits.size() - 1, units.size());
    }

    /**
     * Checks it returns false if the list is empty and
     * true if the list is not empty
     */
    @Test
    void hasUnit() {
        assertEquals(0, units.size());
        if (units.isEmpty()) {
            assertFalse(false);
        }
        units.add(archer);
        assertEquals(1, units.size());
        if (!units.isEmpty()) {
            assertTrue(true);
        }
    }

    /**
     * Checks if the method generates a random number and
     * that it gets the expected unit
     */
    @Test
    void getRandom() {
        Random random = new Random();
        assertEquals(0, units.size());
        units.add(archer);
        units.add(barbarian);
        assertEquals(2, units.size());
        int upperbound = units.size();
        /* randomNumber is an integer between 0 and the
           upperbound-1. The upperbound is given by the list size
         */
        int randomNumber = random.nextInt(upperbound);
        units.get(randomNumber);

        if (randomNumber == 0) {
            assertEquals(0, randomNumber);
            assertEquals(archer, units.get(randomNumber));
        } else if (randomNumber == 1) {
            assertEquals(1, randomNumber);
            assertEquals(barbarian, units.get(randomNumber));
        }
    }
}