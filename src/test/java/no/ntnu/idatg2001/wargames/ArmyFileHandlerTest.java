package no.ntnu.idatg2001.wargames;

import no.ntnu.idatg2001.wargames.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmyFileHandlerTest {
    private static String armyName;
    private static String armyType;
    private static String unitName;
    private static String unitHealth;

    @BeforeEach
    void setUp() {
    }

    public static void getUnitsFromCSV (String filename) {
        boolean isAUnit = true;
        boolean healthIsInt = true;
        List<Unit> unitsFromFile = new ArrayList<>();
        assertEquals(0, unitsFromFile.size());

        Path path = Path.of(filename);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String lineOfText;
            int iterations = 0;
            while ((lineOfText = reader.readLine()) != null && iterations < 2) {
                int i = 0;
                String[] words = lineOfText.split(",");

                Iterator it = Arrays.stream(words).iterator();
                while (it.hasNext()) {
                    words[i] = it.next().toString().strip();
                    i++;
                }
                if (iterations == 0) {
                    assertEquals(null, armyName);
                    armyName = words[0];
                    assertEquals("Human Army", armyName);
                } else {
                    armyType = words[0];
                    unitName = words[1];
                    unitHealth = words[2];
                    if (armyType.equalsIgnoreCase("InfantryUnit")) {
                        unitsFromFile.add(new InfantryUnit(unitName, Integer.parseInt(unitHealth)));
                    } else if (armyType.equalsIgnoreCase("CavalryUnit")) {
                        unitsFromFile.add(new CavalryUnit(unitName, Integer.parseInt(unitHealth)));
                    } else if (armyType.equalsIgnoreCase("CommanderUnit")) {
                        unitsFromFile.add(new CommanderUnit(unitName, Integer.parseInt(unitHealth)));
                    } else if (armyType.equalsIgnoreCase("RangedUnit")) {
                        unitsFromFile.add(new RangedUnit(unitName, Integer.parseInt(unitHealth)));
                    } else {
                        isAUnit = false;
                    }
                }
                iterations++;
            }
        } catch (IOException e) {
            assertThrows(IOException.class, () -> ArmyFileHandler.readCSV(filename), e.getMessage());
        } catch (NumberFormatException e) {
            assertThrows(NumberFormatException.class, () -> ArmyFileHandler.readCSV(filename),
                    "Wrong input" + e.getMessage());
            healthIsInt = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            assertThrows(NullPointerException.class, () -> ArmyFileHandler.readCSV(filename),
                    "Please make sure that you have entered three values, " +
                    "and that all the values are seperated with comma");
        }

        if (isAUnit && healthIsInt)
            assertEquals(1, unitsFromFile.size());
        else
            assertEquals(0, unitsFromFile.size());

        unitsFromFile.forEach(unit -> assertEquals("Footman", unit.getName()));
    }

    public static void writeUnitsToFile(Army army, String filename) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {
            int i = 0;
            for (Unit unit : army.getUnitList()) {
                if (i == 0) {
                    writer.write(army.getName() + "\n");
                } else {
                    writer.write(unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
                }
                i++;
            }
        } catch (IOException e) {
            assertThrows(IOException.class, () -> ArmyFileHandler.writeCSV(army, filename), e.getMessage());
        }
    }

    @Test
    void readCSV() {
        getUnitsFromCSV("src/humanArmy.csv");
    }

    @Test
    void writeCSV() throws IOException {
        List<Unit> units = new ArrayList<>();
        units.add(new CommanderUnit("Mountain King", 180));
        units.add(new InfantryUnit("Footman", 100));
        units.add(new CavalryUnit("Knight", 100));
        units.add(new RangedUnit("Archer", 100));
        writeUnitsToFile(new Army("Orcish Horde Army", units), "src/orcishHordeArmy.csv");
    }
}