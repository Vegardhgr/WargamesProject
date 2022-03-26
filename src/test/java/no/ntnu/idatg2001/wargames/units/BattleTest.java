package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.units.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    private Army strongArmy;
    private Army weakArmy;
    private Army strongArmy2;
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

        battle = new Battle(this.strongArmy, this.weakArmy);
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

    private List<Unit> makeStrongArmy2() {
        List<Unit> strongArmyList2 = new ArrayList<>();
        int infantryUnitsMade = 0;
        int cavalryUnitsMade = 0;
        int rangedUnitsMade = 0;
        int commanderUnitsMade = 0;
        while (infantryUnitsMade < 500) {
            infantryUnits = new InfantryUnit("Footman", 100);
            strongArmyList2.add(infantryUnits);
            infantryUnitsMade++;
        }
        while (cavalryUnitsMade < 100) {
            cavalryUnits = new CavalryUnit("Knight", 100);
            strongArmyList2.add(cavalryUnits);
            cavalryUnitsMade++;
        }
        while (rangedUnitsMade < 200) {
            rangedUnits = new RangedUnit("Archer", 100);
            strongArmyList2.add(rangedUnits);
            rangedUnitsMade++;
        }
        while (commanderUnitsMade < 1) {
            commanderUnits = new CommanderUnit("Mountain King", 180);
            strongArmyList2.add(commanderUnits);
            commanderUnitsMade++;
        }
        return strongArmyList2;
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
    void fairBattle() {
        int strongArmyWins = 0;
        int strongArmy2Wins = 0;
        for (int i = 0; i < 1000; i++) {
            this.strongArmy = new Army("Strong army", makeStrongArmy());
            this.strongArmy2 = new Army("Strong army2", makeStrongArmy2());
            Battle battle = new Battle(strongArmy, strongArmy2);
            winningArmy = battle.simulate().getName();

            if (winningArmy.equals("Strong army")) {
                strongArmyWins++;
            } else {
                strongArmy2Wins++;
            }
        }

        double ratio = ((double) strongArmyWins / (double) strongArmy2Wins) - 1;


        /* If the ratio between strongArmyWins and strongArmy2Wins is more than
           5%, it ain't a fair battle.
         */
        double fairBattle = 0.05;
        if (Math.abs(ratio) < fairBattle) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }
}