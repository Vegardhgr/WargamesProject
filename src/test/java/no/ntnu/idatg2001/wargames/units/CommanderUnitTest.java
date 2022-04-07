package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.Battle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommanderUnitTest {

    // CommanderUnit objects made global
    CommanderUnit archer;
    CommanderUnit barbarian;

    @BeforeEach
    void setUp() {
        Battle.setTerrain(Battle.Terrain.PLAINS);
        // Initializes the global objects
        archer = new CommanderUnit("Archer", 20);
        barbarian = new CommanderUnit("Barbarian", 20);
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
     * Tests that the getResistBonus method returns the correct resist bonus
     */
    @Test
    void getResistBonus() {
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
    void testCommanderUnit(int attackBonus, int resistBonus, Battle.Terrain terrain) {
        Battle.setTerrain(terrain);
        CommanderUnit unit = new CommanderUnit("CommanderUnit", 10);
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
        Unit unit = new CommanderUnit("Commander", 10);
        assertEquals("Unit: Commander | Health: 10 | Attack: 25 | Armor: 15", unit.toString());
    }
}
