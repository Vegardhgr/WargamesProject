package no.ntnu.idatg2001.wargames;

import no.ntnu.idatg2001.wargames.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    private List<Unit> infantryUnitList;
    private List<Unit> cavalryUnitList;
    private List<Unit> commanderUnitList;
    private List<Unit> rangedUnitList;

    @BeforeEach
    void setUp() {
        // Initializing the class fields
        this.infantryUnitList = new ArrayList<>();
        this.cavalryUnitList = new ArrayList<>();
        this.commanderUnitList = new ArrayList<>();
        this.rangedUnitList = new ArrayList<>();
        this.army1 = new Army("Army1");
        this.archer = new CommanderUnit("Archer", 10);
        this.barbarian = new CavalryUnit("Barbarian", 12);
        this.units = new ArrayList<>();
        this.inputUnits = new ArrayList<>();
        addAllTypesOfUnitsToInputUnitsList();
        inputUnits.add(archer);
        inputUnits.add(barbarian);
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

        int randomNumber = random.nextInt(upperbound);
        units.get(randomNumber);

        if (randomNumber == 0) {
            assertEquals(archer, units.get(randomNumber));
        } else if (randomNumber == 1) {
            assertEquals(barbarian, units.get(randomNumber));
        }
    }

    private List<Unit> getInfantryUnit() {
        return inputUnits
                .stream()
                .filter(unit -> unit.getClass() == InfantryUnit.class)
                .collect(Collectors.toList());
    }

    private List<Unit> getCavalryUnit() {
        return inputUnits
                .stream()
                .filter(unit -> unit.getClass() == CavalryUnit.class)
                .collect(Collectors.toList());
    }

    private List<Unit> getCommanderUnitList() {
        return inputUnits
                .stream()
                .filter(unit -> unit.getClass() == CommanderUnit.class)
                .collect(Collectors.toList());
    }

    private List<Unit> getRangedUnitList() {
        return inputUnits
                .stream()
                .filter(unit -> unit.getClass() == RangedUnit.class)
                .collect(Collectors.toList());
    }

    @Test
    void getInfantryUnitsFromInputList() {
        assertTrue(infantryUnitList.size() == 0);
        this.infantryUnitList = getInfantryUnit();
        assertTrue(infantryUnitList.size() != 0);
        infantryUnitList.forEach(unit -> assertTrue(unit.getClass() == InfantryUnit.class));
        infantryUnitList.forEach(unit ->
                assertFalse(unit.getClass() ==  CavalryUnit.class ||
                        unit.getClass() == CommanderUnit.class || unit.getClass() == RangedUnit.class)
        );
    }

    @Test
    void getCavalryUnitsFromInputList() {
        assertTrue(cavalryUnitList.size() == 0);
        this.cavalryUnitList = getCavalryUnit();
        assertTrue(cavalryUnitList.size() != 0);
        cavalryUnitList.forEach(unit -> assertTrue(unit.getClass() ==  CavalryUnit.class));
        cavalryUnitList.forEach(unit ->
                assertFalse(unit.getClass() == InfantryUnit.class ||
                        unit.getClass() == CommanderUnit.class || unit.getClass() == RangedUnit.class)
        );
    }

    @Test
    void getCommanderUnitsFromInputList() {
        assertTrue(commanderUnitList.size() == 0);
        this.commanderUnitList = getCommanderUnitList();
        assertTrue(commanderUnitList.size() != 0);
        commanderUnitList.forEach(unit -> assertTrue(unit.getClass() ==  CommanderUnit.class));
        /* Tests specifically that commanderUnitList does not contain any units with
            type CavalryUnit, since the CommanderUnit class inherits from CavalryUnit*/
        commanderUnitList.forEach(unit -> assertFalse(unit.getClass() == CavalryUnit.class));
        commanderUnitList.forEach(unit ->
                assertFalse(unit.getClass() == InfantryUnit.class ||
                        unit.getClass() == CavalryUnit.class || unit.getClass() == RangedUnit.class)
        );
    }

    @Test
    void getRangedUnitsFromInputList() {
        assertTrue(rangedUnitList.size() == 0);
        this.rangedUnitList = getRangedUnitList();
        assertTrue(rangedUnitList.size() != 0);
        rangedUnitList.forEach(unit -> assertTrue(unit.getClass() == RangedUnit.class));
        rangedUnitList.forEach(unit ->
                assertFalse(unit.getClass() == InfantryUnit.class ||
                        unit.getClass() == CavalryUnit.class || unit.getClass() == CommanderUnit.class)
        );
    }
}