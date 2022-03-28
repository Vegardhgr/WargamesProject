package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.units.units.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This class handles the CSV files
 *
 * @author Vegard Groder
 * @version 20.03.2022
 */
public class ArmyFileHandler {
    private static String armyName;
    private static String armyType;
    private static String unitName;
    private static String unitHealth;

    /**
     * This method reads and handles the content of a file that is passed as
     * a parameter
     * @param filename, the path of the file
     * @return List<Unit>, a list of the units
     * @throws IOException, if the file does not exist
     * @throws NumberFormatException, if a String cannot be parsed to an integer
     * @throws ArrayIndexOutOfBoundsException
     */
    public static Army readCSV(String filename) throws IOException, NumberFormatException,
            ArrayIndexOutOfBoundsException {
        boolean isAUnit = true;
        List<Unit> unitsFromFile = new ArrayList<>();

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
                    armyName = words[0];
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
                        // If armyType does not match any of the army types above
                        isAUnit = false;
                    }
                }
                iterations++;
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Wrong input. " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Please make sure that you have entered three values, " +
                    "and that all the values are seperated with comma");
        }
        if (!isAUnit)
             throw new IllegalArgumentException("Please enter a legal unit type");

        return new Army(armyName,unitsFromFile);
    }

    /**
     * This method writes to a file
     * @param army, an army of units
     * @param filename, a file where the units will be listed
     * @throws IOException, if the file does not exist
     */
    public static void writeCSV(Army army, String filename) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {
            int i = 0;
            for(Unit unit : army.getUnitList()) {
                if (i == 0) {
                    writer.write(army.getName() + "\n");
                } else {
                    writer.write(unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
                }
                i++;
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
