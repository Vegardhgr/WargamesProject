package no.ntnu.idatg2001.wargames.units;

/**
 * A subclass of Unit. This unit, ranged unit, has the
 * advantage that it can attack from distance.
 *
 * @author Vegard Groder
 * @version 10.02.2022
 */
public class RangedUnit extends Unit {

    // Constant variables
    private static final int ATTACK = 15;
    private static final int ARMOR = 8;
    private static final int ATTACK_BONUS = 3;

    private int timesAttacked;

    /**
     * Initializes all the fields in the superclass.
     *
     * @param name Name of the character
     * @param health The character's health
     * @param attack The amount of damage the character does
     * @param armor The amount of health the character regains after being attacked
     */
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A constructor that takes in name and health as parameters
     * and uses the constants ATTACK and ARMOR to define the amount of
     * damage the character does, and how much armor the character has.
     *
     * @param name The characters name
     * @param health The characters health
     */
    public RangedUnit(String name, int health) {
        super(name, health, ATTACK, ARMOR);
    }

    /**
     * Returns the character's attack bonus
     *
     * @return ATTACK_BONUS, it is equal to 3
     */
    @Override
    public int getAttackBonus() {
        return ATTACK_BONUS;
    }

    /**
     * Depending on the amount of times the character is
     * attacked, it returns a resist bonus that is added
     * to its health. After the character is attacked twice,
     * it will return 2.
     *
     * @return Int
     */
    @Override
    public int getResistBonus() {
        if (timesAttacked == 1) {
            return 6;
        } else if (timesAttacked == 2) {
            return 4;
        } else {
            return 2;
        }
    }

    /**
     * Calls the setHealth method in the superclass unit,
     * and then increments times attacked.
     *
     * @param health The character's new health.
     */
    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        timesAttacked++;
    }
}
