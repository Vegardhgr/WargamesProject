package no.ntnu.idatg2001.wargames.core.units;

/**
 * This is an abstract superclass and contains everything that
 * the units, or the subclasses, have in common.
 *
 * @author Vegard Groder
 * @version 10.02.2022
 */
public abstract class Unit {
    /**
     * Fields that all units have in common
     */
    private final String name;
    private int health;
    private int attack;
    private int armor;

    /**
     * Initializes all the fields
     *
     * @param name, name of the unit
     * @param health, the unit's health
     * @param attack, the unit's attack
     * @param armor, the unit's armor
     */
    protected Unit(String name, int health, int attack, int armor) {
        this.name = name;
        setHealth(health);
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * The opponent's health after getting attacked
     *
     * @param opponent, the unit that is getting attacked
     */
    public void attack(Unit opponent) {
        int newHealth = opponent.health - (attack + getAttackBonus()
                + (opponent.armor + opponent.getResistBonus()));
        opponent.setHealth(newHealth);
    }

    /**
     * Returns the name of the unit
     *
     * @return name, the unit's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the health of the unit
     *
     * @return health, unit's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the unit's attack value
     *
     * @return attack, the attack value
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns the unit's armor value
     *
     * @return armor, the armor value
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Sets the character's new health
     *
     * @param health, the new health
     */
    public void setHealth(int health) {
        if (health < 0) {
            this.health = 0;
        } else {
            this.health = health;
        }
    }

    /**
     * Returns how a unit object should be printed
     *
     * @return String, the string representation of the unit
     */
    @Override
    public String toString() {
        return "Unit: " + getName() + " | Health: " + getHealth() +
                " | Attack: " + getAttack() + " | Armor: " + getArmor();
    }

    /**
     * A method all inherited classes must implement
     */
    public abstract int getAttackBonus();

    /**
     * A method all inherited classes must implement
     */
    public abstract int getResistBonus();
}
