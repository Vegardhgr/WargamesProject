package no.ntnu.idatg2001.wargames.utilities;

import no.ntnu.idatg2001.wargames.units.Unit;

/**
 * This class creates a battle between two armies
 *
 * @author Vegard Grøder
 * @version 23.02.2022
 */
public class Battle {
    // Global fields
    private final Army armyOne;
    private final Army armyTwo;
    private Army winningArmy;
    private static Terrain terrain;

    /**
     * Initializes the global army objects
     *
     * @param armyOne, Army object
     * @param armyTwo, Army object
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
    }

    public static Terrain getTerrain() {
        return Battle.terrain;
    }

    public static void setTerrain(Terrain terrain) {
        Battle.terrain = terrain;
    }

    /**
     * Simulates a battle between two armies
     *
     * @return winningArmy, the winner of the battle
     */
    public Army simulate() {
        boolean armyOneAttacks = true;
        boolean fighting = true;
        while (fighting) {
            Unit armyOneUnit = armyOne.getRandom();
            Unit armyTwoUnit = armyTwo.getRandom();
            if (armyOneAttacks) {
                armyOneUnit.attack(armyTwoUnit);
                if (armyTwoUnit.getHealth() <= 0) {
                    armyTwo.remove(armyTwoUnit);
                    if (!armyTwo.hasUnit()) {
                        fighting = false;
                        winningArmy = armyOne;
                        return winningArmy;
                    }
                }
            } else {
                armyTwoUnit.attack(armyOneUnit);
                if (armyOneUnit.getHealth() <= 0) {
                    armyOne.remove(armyOneUnit);
                    if (!armyOne.hasUnit()) {
                        fighting = false;
                        winningArmy = armyTwo;
                        return winningArmy;
                    }
                }
            }
            armyOneAttacks = !armyOneAttacks;
        }
        return null;
    }

    /**
     * Simulates a battle between two units
     *
     * @param attacker, the attacking unit
     * @param defender, the defending unit
     */
    public boolean oneStepBattle(Unit attacker, Unit defender) {
        attacker.attack(defender);
        if (defender.getHealth() <= 0)
            return true;
        return false;
    }

    /**
     * Before a battle, it returns which armies that are fighting.
     * After a battle it returns the winning army
     *
     * @return String
     */
    @Override
    public String toString() {
        if (winningArmy == null) {
            return armyOne.getName() + " vs " + armyTwo.getName();
        } else {
            return "The winning army is " + winningArmy.getName();
        }
    }

    public enum Terrain {
        FOREST,
        HILL,
        PLAINS
    }
}

