package no.ntnu.idatg2001.wargames.units.units;

import no.ntnu.idatg2001.wargames.units.units.RangedUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the RangedUnit class.
 *
 * @author Vegard Grøder
 * @version 10.02.2022
 */
class RangedUnitTest {
    
    // RangedUnit objects made global
    RangedUnit archer;
    RangedUnit barbarian;

    @BeforeEach
    void setUp() {
        // Initializes the RangedUnit objects
        archer = new RangedUnit("Archer", 13);
        barbarian = new RangedUnit("Barbarian", 15);
    }

    /**
     * Tests that it returns the right attack bonus.
     */
    @Test
    void getAttackBonus() {
        assertEquals(3, archer.getAttackBonus());
        archer.attack(barbarian);
        assertEquals(3, archer.getAttackBonus());
    }

    /**
     * Tests that the amount of resist bonus that the
     * method returns is correct
     */
    @Test
    void getResistBonus() {
        archer.attack(barbarian);
        assertEquals(6, barbarian.getResistBonus());
        archer.attack(barbarian);
        assertEquals(4, barbarian.getResistBonus());
        archer.attack(barbarian);
        assertEquals(2, barbarian.getResistBonus());
        archer.attack(barbarian);
        assertEquals(2, barbarian.getResistBonus());
    }
}