package no.ntnu.idatg2001.wargames.core.units;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates units.
 *
 * @author Vegard Grøder
 * @version 1.0.0
 */
public class UnitFactory {
    //The instance of this class
    private static volatile UnitFactory instance;

    // Private constructor to prevent instantiation
    private UnitFactory() {}

    /**
     * Creates only one UnitFactory object
     * @return UnitFactory, the only instance of UnitFactory
     */
    public static UnitFactory getInstance() {
        if (UnitFactory.instance == null) {
            synchronized (UnitFactory.class) {
                UnitFactory.instance = new UnitFactory();
            }
        }
        return UnitFactory.instance;
    }

    /**
     * Creates one unit of the given type
     * @param unitType, the type of unit to create
     * @param name, the name of the unit
     * @param health, the health of the unit
     * @return Unit, one unit object
     */
    public Unit createOneUnit(UnitType unitType, String name, int health) {
        return switch (unitType) {
            case INFANTRYUNIT -> new InfantryUnit(name, health);
            case CAVALRYUNIT -> new CavalryUnit(name, health);
            case RANGEDUNIT -> new RangedUnit(name, health);
            case COMMANDERUNIT -> new CommanderUnit(name, health);
        };
    }

    /**
     * Creates and returns a list of units of the given type
     * @param unitType, the type of units to create
     * @param name, the name of the units
     * @param health, the health of the units
     * @param number, the number of units to be created
     * @return List<Unit>, a list of unit objects
     */
    public List<Unit> createMultipleUnits(UnitType unitType, String name, int health, int number) {
        List<Unit> units = new ArrayList<>();
        switch (unitType) {
            case INFANTRYUNIT:
                for (int i = 0; i < number; i++) {
                    units.add(new InfantryUnit(name, health));
                }
                break;
            case CAVALRYUNIT:
                for (int i = 0; i < number; i++) {
                    units.add(new CavalryUnit(name, health));
                }
                break;
            case RANGEDUNIT:
                for (int i = 0; i < number; i++) {
                    units.add(new RangedUnit(name, health));
                }
                break;
            case COMMANDERUNIT:
                for (int i = 0; i < number; i++) {
                    units.add(new CommanderUnit(name, health));
                }
        }
        return units;
    }

    /**
     * An enum containing the types of units
     */
    public enum UnitType {
        INFANTRYUNIT,
        CAVALRYUNIT,
        RANGEDUNIT,
        COMMANDERUNIT
    }
}
