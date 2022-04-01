package no.ntnu.idatg2001.wargames.units;

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
     * @param name
     * @param health
     * @param attack
     * @param armor
     */
    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        setHealth(health);
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * The opponent's health after getting attacked
     *
     * @param opponent
     */
    public void attack(Unit opponent) {
        int health = opponent.health - (attack + getAttackBonus()) + (opponent.armor + opponent.getResistBonus());
        opponent.setHealth(health);
    }

    /**
     * Returns the name of the character
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the health of the character
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns attack value
     *
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns the armor value
     *
     * @return armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Sets the character's new health
     *
     * @param health
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
     * @return String
     */
    @Override
    public String toString() {
        return "Unit: " + getName() + " | Health: " + getHealth() +
                " | Attack: " + getAttack() + " | Armor: " + getArmor();
    }

    public abstract int getAttackBonus();

    public abstract int getResistBonus();
}