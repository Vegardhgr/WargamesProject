package no.ntnu.idatg2001.wargames.utilities;

import no.ntnu.idatg2001.wargames.units.*;
import no.ntnu.idatg2001.wargames.utilities.Army;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    private Army army;
    private Unit archer;
    private Unit barbarian;
    private List<Unit> inputUnits;

    @BeforeEach
    void setUp() {
        // Initializing the class fields
        this.army = new Army("Army1");
        this.archer = new CommanderUnit("Archer", 10);
        this.barbarian = new CavalryUnit("Barbarian", 12);
        this.inputUnits = new ArrayList<>();
        addAllTypesOfUnitsToInputUnitsList();
    }

    private void addAllTypesOfUnitsToInputUnitsList() {
        CommanderUnit mountainKing = new CommanderUnit("Mountain King", 180);
        CavalryUnit knight = new CavalryUnit("Knight", 100);
        InfantryUnit footman = new InfantryUnit("Footman", 100);
        RangedUnit spearman = new RangedUnit("Spearman", 100);
        inputUnits.add(mountainKing);
        inputUnits.add(knight);
        inputUnits.add(footman);
        inputUnits.add(spearman);
    }

    /**
     * Tests that it returns the expected value
     */
    @Test
    void getName() {
        assertEquals("Army1", army.getName());
    }

    @Test
    void getUnitListTest() {
        assertEquals(0, army.getUnitList().size());
        army.add(new CommanderUnit("Dan'gul", 200));
        assertEquals(1, army.getUnitList().size());
    }

    /**
     * Tests that it adds units to an army
     */
    @Test
    void add() {
        assertEquals(0, army.getUnitList().size());
        army.add(archer);
        assertEquals(1, army.getUnitList().size());
        army.add(barbarian);
        assertEquals(2, army.getUnitList().size());
    }

    /**
     * Tests the second constructor in the army class, that includes
     * both name and a list of units as parameters.
     */
    @Test
    void testSecondConstructor() {
        Army army2 = new Army("Army2", inputUnits);
        assertEquals(4, army2.getUnitList().size());
        inputUnits.add(new CavalryUnit("Cavalry", 24));
        assertEquals(5, army2.getUnitList().size());
    }

    /**
     * Tests that it adds all the units from a list, into the army
     */
    @Test
    void addAllTest() {
        assertEquals(4, inputUnits.size());
        assertEquals(0, army.getUnitList().size());
        army.addAll(inputUnits);
        assertEquals(4, army.getUnitList().size());
    }

    /**
     * Tests that it removes one element from the army
     */
    @Test
    void removeRandomElement() {
        assertEquals(4, inputUnits.size());
        assertEquals(0, army.getUnitList().size());
        army.addAll(inputUnits);
        assertEquals(4, army.getUnitList().size());
        Random randNr = new Random();
        int upperbound = army.getUnitList().size();
        int randomNumber = randNr.nextInt(upperbound);
        assertEquals(4, army.getUnitList().size());
        army.remove(army.getUnitList().get(randomNumber));
        assertEquals(3, army.getUnitList().size());
    }

    /**
     * Tests that it gets a random unit from army list
     */
    @Test
    void getRandomTest() {
        assertEquals(4, inputUnits.size());
        assertEquals(0, army.getUnitList().size());
        army.addAll(inputUnits);
        assertEquals(4, army.getUnitList().size());
        Unit randomUnit = army.getRandom();
        switch (randomUnit.getClass().getSimpleName().toUpperCase()) {
            case "INFATRYUNIT":
                assertEquals(InfantryUnit.class.getSimpleName(), randomUnit.getClass().getSimpleName());
                break;
            case "CAVALRYUNIT":
                assertEquals(CavalryUnit.class.getSimpleName(), randomUnit.getClass().getSimpleName());
                break;
            case "COMMANDERUNIT":
                assertEquals(CommanderUnit.class.getSimpleName(), randomUnit.getClass().getSimpleName());
                break;
            case "RANGEDUNIT":
                assertEquals(RangedUnit.class.getSimpleName(), randomUnit.getClass().getSimpleName());
        }
    }

    /**
     * Checks that hasUnit returns false if the list is empty and
     * true if the list is not empty
     */
    @Test
    void hasUnitTest() {
        assertEquals(0, army.getUnitList().size());
        // List is empty if army1.hasUnit() returns false
        assertFalse(army.hasUnit());
        //List is not empty if army1.hasUnit() returns true
        army.addAll(inputUnits);
        assertTrue(army.hasUnit());
        assertEquals(4, army.getUnitList().size());
    }

    @Test
    void getInfantryUnitsFromInputList() {
        List<Unit> infantryUnitList = new ArrayList<>();
        assertEquals(0, infantryUnitList.size());
        army.addAll(inputUnits);
        infantryUnitList = army.getInfantryUnit();
        assertEquals(1, infantryUnitList.size());
        infantryUnitList.forEach(unit -> assertSame(unit.getClass(), InfantryUnit.class));
        infantryUnitList.forEach(unit ->
                assertFalse(unit.getClass() ==  CavalryUnit.class ||
                        unit.getClass() == CommanderUnit.class || unit.getClass() == RangedUnit.class)
        );
    }

    @Test
    void getCavalryUnitsFromInputList() {
        List<Unit> cavalryUnitList = new ArrayList<>();
        assertEquals(0, cavalryUnitList.size());
        army.addAll(inputUnits);
        cavalryUnitList = army.getCavalryUnit();
        assertEquals(1, cavalryUnitList.size());
        cavalryUnitList.forEach(unit -> assertSame(unit.getClass(), CavalryUnit.class));
        cavalryUnitList.forEach(unit ->
                assertFalse(unit.getClass() == InfantryUnit.class ||
                        unit.getClass() == CommanderUnit.class || unit.getClass() == RangedUnit.class)
        );
    }

    @Test
    void getCommanderUnitsFromInputList() {
        List<Unit> commanderUnitList = new ArrayList<>();
        assertEquals(0, commanderUnitList.size());
        army.addAll(inputUnits);
        commanderUnitList = army.getCommanderUnitList();
        assertEquals(1, commanderUnitList.size());
        commanderUnitList.forEach(unit -> assertSame(unit.getClass(), CommanderUnit.class));
        /* Tests specifically that commanderUnitList does not contain any units with
            type CavalryUnit, since the CommanderUnit class inherits from CavalryUnit*/
        commanderUnitList.forEach(unit -> assertNotSame(unit.getClass(), CavalryUnit.class));
        commanderUnitList.forEach(unit ->
                assertFalse(unit.getClass() == InfantryUnit.class ||
                        unit.getClass() == CavalryUnit.class || unit.getClass() == RangedUnit.class)
        );
    }

    @Test
    void getRangedUnitsFromInputList() {
        List<Unit> rangedUnitList = new ArrayList<>();
        assertEquals(0, rangedUnitList.size());
        army.addAll(inputUnits);
        rangedUnitList = army.getRangedUnitList();
        assertEquals(1, rangedUnitList.size());
        rangedUnitList.forEach(unit -> assertSame(unit.getClass(), RangedUnit.class));
        rangedUnitList.forEach(unit ->
                assertFalse(unit.getClass() == InfantryUnit.class ||
                        unit.getClass() == CavalryUnit.class || unit.getClass() == CommanderUnit.class)
        );
    }

    @Test
    void checkForDifferentArmies() {
        Army army = new Army("Army",inputUnits);
        Army army2 = new Army(army);
        assertNotSame(army, army2);
        assertEquals(army, army2);
    }
}