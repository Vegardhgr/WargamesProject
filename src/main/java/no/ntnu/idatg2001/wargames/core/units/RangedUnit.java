package no.ntnu.idatg2001.wargames.core.units;

import no.ntnu.idatg2001.wargames.core.Battle;

/**
 * A subclass of Unit. This unit, ranged unit, has the
 * advantage that it can attack from distance.
 *
 * @author Vegard Groder
 * @version 1.0.0
 */
public class RangedUnit extends Unit {

    // Constant variables
    private static final int ATTACK = 15;
    private static final int ARMOR = 8;
    private static final int ATTACK_BONUS = 3;
    private static final int ATTACK_BONUS_TERRAIN = 2;
    private int timesAttacked;

    /**
     * Initializes all the fields in the superclass.
     *
     * @param name, name of the character
     * @param health, the character's health
     * @param attack, the amount of damage the character does
     * @param armor, the amount of health the character regains after being attacked
     */
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
        this.timesAttacked = 0;
    }

    /**
     * A constructor that takes in name and health as parameters
     * and uses the constants ATTACK and ARMOR to define the amount of
     * damage the character does, and how much armor the character has.
     *
     * @param name, the character's name
     * @param health, the character's health
     */
    public RangedUnit(String name, int health) {
        super(name, health, ATTACK, ARMOR);
        this.timesAttacked = 0;
    }

    /**
     * Returns the character's attack bonus
     *
     * @return ATTACK_BONUS, it is equal to 3
     */
    @Override
    public int getAttackBonus() {
        if (Battle.getTerrain().equals(Battle.Terrain.HILL))
            return ATTACK_BONUS + ATTACK_BONUS_TERRAIN;
        else if (Battle.getTerrain().equals(Battle.Terrain.FOREST))
            return ATTACK_BONUS - 1;
        return ATTACK_BONUS;
    }

    /**
     * Depending on the amount of times the character is
     * attacked, it returns a resist bonus that is added
     * to its health. After the character is attacked twice,
     * it will return 2.
     *
     * @return int, the resist bonus
     */
    @Override
    public int getResistBonus() {
        if (timesAttacked == 0) {
            return 6;
        } else if (timesAttacked == 1) {
            return 4;
        } else {
            return 2;
        }
    }

    /**
     * Calls the setHealth method in the superclass unit,
     * and then increments times attacked.
     *
     * @param health, the character's new health.
     */
    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        timesAttacked++;
    }
}
