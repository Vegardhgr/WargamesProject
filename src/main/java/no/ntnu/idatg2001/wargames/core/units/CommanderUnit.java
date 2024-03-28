package no.ntnu.idatg2001.wargames.core.units;

/**
 * A subclass of CavalryUnit. This unit, commander unit, is a better version
 * of cavalry units due to increased damage when attacking and more armor.
 *
 * @author Vegard Groder
 * @version 1.0.0
 */
public class CommanderUnit extends CavalryUnit {

    // Constant variables
    private static final int ATTACK = 25;
    private static final int ARMOR = 15;

    /**
     * Initializes all the fields in the superclass.
     *
     * @param name   Name of the character
     * @param health The character's health
     * @param attack The amount of damage the character does
     * @param armor  The amount of health the character regains after being attacked
     */
    public CommanderUnit(String name, int health, int attack, int armor) {
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
    public CommanderUnit(String name, int health) {
        super(name, health, ATTACK, ARMOR);
    }
}
