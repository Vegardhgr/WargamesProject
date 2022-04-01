package no.ntnu.idatg2001.wargames.units;

/**
 * A subclass of Unit. This unit, cavalry unit, has the
 * advantage that it does more damage the first time it
 * attacks.
 *
 * @author Vegard Groder
 * @version 10.02.2022
 */
public class CavalryUnit extends Unit {

    // Constant variables
    private static final int ATTACK = 20;
    private static final int ARMOR = 12;
    private static final int ATTACK_BONUS = 2;
    private static final int RESIST_BONUS = 1;

    // Keeps track of how many times a cavalry unit has attacked an opponent
    private int attackCounter;

    /**
     * Initializes all the fields in the superclass.
     *
     * @param name   Name of the character
     * @param health The character's health
     * @param attack The amount of damage the character does
     * @param armor  The amount of health the character regains after being attacked
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
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
    public CavalryUnit(String name, int health) {
        super(name, health, ATTACK, ARMOR);
    }

    /**
     * A getter for attackCounter
     *
     * @return attackCounter
     */
    public int getAttackCounter() {
        return attackCounter;
    }

    /**
     * Depended on how many times the character has attacked,
     * this method returns a damage bonus.
     */
    @Override
    public int getAttackBonus() {
        if (attackCounter == 1) {
            return (ATTACK_BONUS + 4);
        }
        return ATTACK_BONUS;
    }

    /**
     * Returns the character's resist bonus.
     *
     * @return RESIST_BONUS, it is equal to 1
     */
    @Override
    public int getResistBonus() {
        return RESIST_BONUS;
    }

    /**
     * Calls the attack method in the superclass, and then
     * increments the attack counter.
     *
     * @param opponent
     */
    @Override
    public void attack(Unit opponent) {
        super.attack(opponent);
        attackCounter++;
    }


}
