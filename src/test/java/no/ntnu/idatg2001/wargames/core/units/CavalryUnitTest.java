package no.ntnu.idatg2001.wargames.core.units;

import no.ntnu.idatg2001.wargames.core.Battle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
        Battle.setTerrain(Battle.Terrain.PLAINS);
        // Initializes the global objects
        archer = new CavalryUnit("Archer", 10, 3, 1);
        barbarian = new CavalryUnit("Barbarian", 11, 2, 2);
    }

    /**
     * This test makes sure the expected attack bonus is returned
     */
    @Test
    void getAttackBonus() {
        assertEquals(8, barbarian.getAttackBonus());
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

    /**
     * Tests that the correct amount of bonuses is returned
     * depending on the terrain.
     */
    @ParameterizedTest
    @MethodSource("attackAndResistBonus")
    void testCavalryUnit(int attackBonus, int resistBonus, Battle.Terrain terrain) {
        Battle.setTerrain(terrain);
        CavalryUnit unit = new CavalryUnit("CavalryUnit", 10);
        assertEquals(attackBonus, unit.getAttackBonus());
        assertEquals(resistBonus, unit.getResistBonus());
    }

    /**
     * Sends arguments with three parameters as a stream
     * @return Stream<Arguments>, a stream of arguments with three parameters
     */
    private static Stream<Arguments> attackAndResistBonus() {
        return Stream.of(
                Arguments.of(2,0, Battle.Terrain.FOREST),
                Arguments.of(2,1, Battle.Terrain.HILL),
                Arguments.of(8,1, Battle.Terrain.PLAINS)
        );
    }

    @Test
    void toStringTest() {
        Unit unit = new CavalryUnit("Cavalry", 10);
        assertEquals("Unit: Cavalry | Health: 10 | Attack: 20 | Armor: 12", unit.toString());
    }
}