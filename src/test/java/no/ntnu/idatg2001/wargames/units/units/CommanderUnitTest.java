package no.ntnu.idatg2001.wargames.units.units;

import no.ntnu.idatg2001.wargames.units.units.CommanderUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommanderUnitTest {

    // CommanderUnit objects made global
    CommanderUnit archer;
    CommanderUnit barbarian;

    @BeforeEach
    void setUp() {
        // Initializes the global objects
        archer = new CommanderUnit("Archer", 20);
        barbarian = new CommanderUnit("Barbarian", 20);
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
     * Tests that the getResistBonus method returns the correct resist bonus
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
        assertEquals(1,barbarian.getAttackCounter());
    }
}
