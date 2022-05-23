package no.ntnu.idatg2001.wargames.core.units;

import no.ntnu.idatg2001.wargames.core.units.Unit;
import no.ntnu.idatg2001.wargames.core.units.UnitFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class UnitFactoryTest {
    static UnitFactory factory;
    @BeforeAll
    static void init() {
        factory = UnitFactory.getInstance();
    }

    @Test
    void createUnit() {
        Unit unit = factory.createOneUnit(UnitFactory.UnitType.CAVALRYUNIT, "CavalryUnit", 10);
        Assertions.assertEquals("CavalryUnit", unit.getClass().getSimpleName());
    }

    @Test
    void createMultipleUnits() {
        List<Unit> unitList = factory.createMultipleUnits(UnitFactory.UnitType.CAVALRYUNIT, "CavalryUnit", 10, 10);
        Assertions.assertEquals(10, unitList.size());
    }
}