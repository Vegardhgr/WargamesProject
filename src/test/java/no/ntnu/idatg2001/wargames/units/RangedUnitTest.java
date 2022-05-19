package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.utilities.Battle;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the RangedUnit class.
 *
 * @author Vegard Grøder
 * @version 10.02.2022
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RangedUnitTest {

    // RangedUnit objects made global
    RangedUnit archer;
    RangedUnit barbarian;

    @BeforeAll
    void setUp() {
        //Setting a default terrain
        Battle.setTerrain(Battle.Terrain.FOREST);
        // Initializes the RangedUnit objects
        archer = new RangedUnit("Archer", 13);
        barbarian = new RangedUnit("Barbarian", 15);
    }

    /**
     * Tests that it returns the right attack bonus.
     */
    @Test
    void getAttackBonus() {
        assertEquals(2, archer.getAttackBonus());
    }

    /**
     * Tests that the amount of resist bonus that the
     * method returns is correct
     */
    @Test
    void getResistBonus() {
        assertEquals(6, barbarian.getResistBonus());
        archer.attack(barbarian);
        assertEquals(4, barbarian.getResistBonus());
        archer.attack(barbarian);
        assertEquals(2, barbarian.getResistBonus());
        archer.attack(barbarian);
        assertEquals(2, barbarian.getResistBonus());
    }

    /**
     * Tests that the correct amount of bonuses is returned
     * depending on the terrain.
     */
    @ParameterizedTest
    @MethodSource("attackAndResistBonus")
    void testRangedUnit(int attackBonus, int resistBonus, Battle.Terrain terrain) {
        Battle.setTerrain(terrain);
        RangedUnit unit = new RangedUnit("RangedUnit", 10);
        assertEquals(attackBonus, unit.getAttackBonus());
        assertEquals(resistBonus, unit.getResistBonus());
    }

    /**
     * Sends arguments with three parameters as a stream
     * @return Stream<Arguments>, a stream of arguments with three parameters
     */
    private static Stream<Arguments> attackAndResistBonus() {
        return Stream.of(
                Arguments.of(2,6, Battle.Terrain.FOREST),
                Arguments.of(5,6, Battle.Terrain.HILL),
                Arguments.of(3,6, Battle.Terrain.PLAINS)
        );
    }

    @Test
    void toStringTest() {
        Unit unit = new RangedUnit("Ranged", 10);
        assertEquals("Unit: Ranged | Health: 10 | Attack: 15 | Armor: 8", unit.toString());
    }
}