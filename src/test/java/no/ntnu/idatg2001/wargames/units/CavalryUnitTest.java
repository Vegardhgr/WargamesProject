package no.ntnu.idatg2001.wargames.units;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the CavalryUnit class.
 *
 * @author Vegard Grøder
 * @version 10.02.2022
 */
class CavalryUnitTest {

    // CavalryUnit objects made global
    CavalryUnit archer;
    CavalryUnit barbarian;

    @BeforeEach
    void setUp() {
        // Initializes the global objects
        archer = new CavalryUnit("Archer", 10, 3, 1);
        barbarian = new CavalryUnit("Barbarian", 11, 2, 2);
    }

    /**
     * This test makes sure the expected attack bonus is returned
     */
    @Test
    void getAttackBonus() {
        barbarian.attack(archer);
        assertEquals(6, barbarian.getAttackBonus());
        barbarian.attack(archer);
        assertEquals(2, barbarian.getAttackBonus());
        barbarian.attack(archer);
        assertEquals(2, barbarian.getAttackBonus());
    }

    /**
     * Tests that the method returns the correct resist bonus
     */
    @Test
    void getResistBonus() {
        barbarian.attack(archer);
        assertEquals(1, barbarian.getResistBonus());
    }

    /**
     * Tests that the attack counter gets incremented every time
     * a unit attacks an opponent.
     */
    @Test
    void attack() {
        assertEquals(0, barbarian.getAttackCounter());
        barbarian.attack(archer);
        assertEquals(1, barbarian.getAttackCounter());
    }

    @Test
    void toStringTest() {
        Unit unit = new CavalryUnit("Cavalry", 10);
        assertEquals("Unit: Cavalry | Health: 10 | Attack: 20 | Armor: 12", unit.toString());
    }
}