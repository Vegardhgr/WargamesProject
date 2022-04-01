package no.ntnu.idatg2001.wargames.units;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {
    Unit unit;

    @BeforeEach
    void setUp() {
        unit = new Unit("Archer", 100, 10, 5) {
            @Override
            public int getAttackBonus() {
                return 0;
            }

            @Override
            public int getResistBonus() {
                return 0;
            }
        };
        assertEquals("Archer", unit.getName());
    }

    @Test
    void attack() {
        Unit unit2 = new Unit("Swordsman", 120, 15, 7) {
            @Override
            public int getAttackBonus() {
                return 0;
            }

            @Override
            public int getResistBonus() {
                return 0;
            }
        };

        assertEquals(100, unit.getHealth());
        unit2.attack(unit);
        assertEquals(90, unit.getHealth());
    }

    @Test
    void getName() {
        assertEquals("Archer", unit.getName());
    }

    @Test
    void getHealth() {
        assertEquals(100, unit.getHealth());
    }

    @Test
    void getAttack() {
        assertEquals(10, unit.getAttack());
    }

    @Test
    void getArmor() {
        assertEquals(5, unit.getArmor());

    }

    @Test
    void setHealth() {
        unit.setHealth(unit.getHealth() - 5);
        assertEquals(95, unit.getHealth());
        unit.setHealth(-100);
        assertEquals(0, unit.getHealth());
    }

    @Test
    void getAttackBonus() {
    }

    @Test
    void getResistBonus() {

    }
}