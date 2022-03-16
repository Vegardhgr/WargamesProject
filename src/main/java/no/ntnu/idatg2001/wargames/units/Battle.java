package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.units.units.Unit;

/**
 * This class creates a battle between two armies
 *
 * @author Vegard Grøder
 * @version 23.02.2022
 */
public class Battle {
    // Global fields
    private Army armyOne;
    private Army armyTwo;
    private Army winningArmy;

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
                    //System.out.println("A orcish horde unit down");
                    // Checking if armyTwo has more units left
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
                    //System.out.println("A human army unit down");
                    // Checking if armyOne has more units left
                    if (!armyOne.hasUnit()) {
                        fighting = false;
                        winningArmy = armyTwo;
                        return winningArmy;
                    }
                }
            }
            // System.out.println("Human army unit: " + armyOneUnit.getHealth());
            // System.out.println("Orcish horde unit: " + armyTwoUnit.getHealth());
            // Changing who is the attacker
            armyOneAttacks = !armyOneAttacks;
        }
        return null;
    }

    /**
     * Before a battle, it returns which armies that are fighting.
     * After a battle it returnes the winning army
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


}

