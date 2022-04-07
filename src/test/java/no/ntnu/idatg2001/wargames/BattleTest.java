package no.ntnu.idatg2001.wargames;

import no.ntnu.idatg2001.wargames.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    private Army strongArmy;
    private Army weakArmy;
    private String winningArmy;
    private Battle battle;

    private InfantryUnit infantryUnits;
    private RangedUnit rangedUnits;
    private CavalryUnit cavalryUnits;
    private CommanderUnit commanderUnits;

    @BeforeEach
    void setUp() {
        this.strongArmy = new Army("Strong army", makeStrongArmy());
        this.weakArmy = new Army("Weak army", makeWeakArmy());

        this.battle = new Battle(this.strongArmy, this.weakArmy);
    }

    private List<Unit> makeStrongArmy() {
        List<Unit> strongArmyList = new ArrayList<>();
        int infantryUnitsMade = 0;
        int cavalryUnitsMade = 0;
        int rangedUnitsMade = 0;
        int commanderUnitsMade = 0;
        while (infantryUnitsMade < 500) {
            infantryUnits = new InfantryUnit("Footman", 100);
            strongArmyList.add(infantryUnits);
            infantryUnitsMade++;
        }
        while (cavalryUnitsMade < 100) {
            cavalryUnits = new CavalryUnit("Knight", 100);
            strongArmyList.add(cavalryUnits);
            cavalryUnitsMade++;
        }
        while (rangedUnitsMade < 200) {
            rangedUnits = new RangedUnit("Archer", 100);
            strongArmyList.add(rangedUnits);
            rangedUnitsMade++;
        }
        while (commanderUnitsMade < 1) {
            commanderUnits = new CommanderUnit("Mountain King", 180);
            strongArmyList.add(commanderUnits);
            commanderUnitsMade++;
        }
        assertEquals(801, strongArmyList.size());
        return strongArmyList;
    }

    private List<Unit> makeWeakArmy() {
        List<Unit> weakArmyList = new ArrayList<>();
        int infantryUnitsMade = 0;
        int cavalryUnitsMade = 0;
        int rangedUnitsMade = 0;
        int commanderUnitsMade = 0;
        while (infantryUnitsMade < 500) {
            infantryUnits = new InfantryUnit("Footman", 10);
            weakArmyList.add(infantryUnits);
            infantryUnitsMade++;
        }
        while (cavalryUnitsMade < 100) {
            cavalryUnits = new CavalryUnit("Knight", 10);
            weakArmyList.add(cavalryUnits);
            cavalryUnitsMade++;
        }
        while (rangedUnitsMade < 200) {
            rangedUnits = new RangedUnit("Archer", 10);
            weakArmyList.add(rangedUnits);
            rangedUnitsMade++;
        }
        while (commanderUnitsMade < 1) {
            commanderUnits = new CommanderUnit("Mountain King", 18);
            weakArmyList.add(commanderUnits);
            commanderUnitsMade++;
        }
        assertEquals(801, weakArmyList.size());
        return weakArmyList;
    }

    //Lag en init der man lager et battle objekt.

    @Test
    void simulate() {
        setUp();
        winningArmy = battle.simulate().getName();
        assertEquals("Strong army", winningArmy);
    }

    @Test
    void testToString() {
        setUp();
        assertEquals("Strong army vs Weak army", battle.toString());
        battle.simulate();

        String winningArmy = battle.toString();
        assertEquals("The winning army is " + strongArmy.getName(), winningArmy);
    }

    @Test
    void setTerrainForest() {
        this.battle.setTerrain(Battle.Terrain.FOREST);
        assertEquals(Battle.Terrain.FOREST, this.battle.getTerrain());
    }

    @Test
    void setTerrainHill() {
        this.battle.setTerrain(Battle.Terrain.HILL);
        assertEquals(Battle.Terrain.HILL, battle.getTerrain());
    }

    @Test
    void setTerrainPlains() {
        this.battle.setTerrain(Battle.Terrain.PLAINS);
        assertEquals(Battle.Terrain.PLAINS, battle.getTerrain());
    }
}