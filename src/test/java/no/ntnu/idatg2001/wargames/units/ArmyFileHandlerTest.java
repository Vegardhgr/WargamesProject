package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.units.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
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

    @Test
    void readCSV() {
        getUnitsFromCSV("src/humanArmy.csv");
    }
}