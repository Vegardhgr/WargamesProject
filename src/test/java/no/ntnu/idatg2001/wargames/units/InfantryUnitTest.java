package no.ntnu.idatg2001.wargames.units;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the InfantryUnit class.
 *
 * @author Vegard Grøder
 * @version 10.02.2022
 */
class InfantryUnitTest {
    // A InfantryUnit object made global
    InfantryUnit barbarian;

    @BeforeEach
    void setUp() {
        // Initializes the barbarian object
        barbarian = new InfantryUnit("Barbarian", 12);
    }

    /**
     * Tests the attack bonus method
     */
    @Test
    void getAttackBonus() {
        assertEquals(2, barbarian.getAttackBonus());
    }

    /**
     * Tests the resist bonus method
     */
    @Test
    void getResistBonus() {
        assertEquals(1, barbarian.getResistBonus());
    }
}