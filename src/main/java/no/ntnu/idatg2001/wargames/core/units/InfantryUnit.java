package no.ntnu.idatg2001.wargames.core.units;

import no.ntnu.idatg2001.wargames.core.Battle;

/**
 * A subclass of Unit. This unit, infantry unit, has its
 * advantage in close combat.
 *
 * @author Vegard Groder
 * @version 1.0.0
 */
public class InfantryUnit extends Unit {

    // Constant variables
    private static final int ATTACK = 15;
    private static final int ARMOR = 10;
    private static final int ATTACK_BONUS = 2;
    private static final int RESIST_BONUS = 1;
    private static final int ATTACK_BONUS_TERRAIN = 1;
    private static final int RESIST_BONUS_TERRAIN = 1;

    /**
     * Initializes all the fields for the Infantry unit
     *
     * @param name   Name of the character
     * @param health The health the character has
     * @param attack The amount of damage the character does
     * @param armor  Extra health for the character
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A constructor that takes in name and health as parameters
     * and uses the constants ATTACK and ARMOR to define the amount of
     * damage the character does, and how much armor the character has.
     *
     * @param name   The characters name
     * @param health The characters health
     */
    public InfantryUnit(String name, int health) {
        super(name, health, ATTACK, ARMOR);
    }

    /**
     * Returns the attack bonus
     *
     * @return ATTACK_BONUS, it is equal to 2.
     */
    @Override
    public int getAttackBonus() {
        if (Battle.getTerrain().equals(Battle.Terrain.FOREST))
            return ATTACK_BONUS + ATTACK_BONUS_TERRAIN;
        return ATTACK_BONUS;
    }

    /**
     * Returns the resist bonus.
     *
     * @return RESIST_BONUS, it is equal to 1.
     */
    @Override
    public int getResistBonus() {
        if (Battle.getTerrain().equals(Battle.Terrain.FOREST))
            return RESIST_BONUS + RESIST_BONUS_TERRAIN;
        return RESIST_BONUS;
    }
}
