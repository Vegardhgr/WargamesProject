package no.ntnu.idatg2001.wargames.core.units;

import no.ntnu.idatg2001.wargames.core.Battle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the InfantryUnit class.
 *
 * @author Vegard Grøder
 * @version 10.02.2022
 */
class InfantryUnitTest {
    /**
     * Tests that the correct amount of bonuses is returned
     * depending on the terrain.
     */
    @ParameterizedTest
    @MethodSource("attackAndResistBonus")
    void testInfantryUnit(int attackBonus, int resistBonus, Battle.Terrain terrain) {
        Battle.setTerrain(terrain);
        InfantryUnit unit = new InfantryUnit("InfantryUnit", 10);
        assertEquals(attackBonus, unit.getAttackBonus());
        assertEquals(resistBonus, unit.getResistBonus());
    }

    /**
     * Sends arguments with three parameters as a stream
     * @return Stream<Arguments>, a stream of arguments with three parameters
     */
    private static Stream<Arguments> attackAndResistBonus() {
        return Stream.of(
                Arguments.of(3,2, Battle.Terrain.FOREST),
                Arguments.of(2,1, Battle.Terrain.HILL),
                Arguments.of(2,1, Battle.Terrain.PLAINS)
        );
    }

    @Test
    void toStringTest() {
        Unit unit = new InfantryUnit("Infantry", 10);
        assertEquals("Unit: Infantry | Health: 10 | Attack: 15 | Armor: 10", unit.toString());
    }
}