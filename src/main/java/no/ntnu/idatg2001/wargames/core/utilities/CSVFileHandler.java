package no.ntnu.idatg2001.wargames.core.utilities;

import no.ntnu.idatg2001.wargames.core.Army;
import no.ntnu.idatg2001.wargames.core.units.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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
public class CSVFileHandler {

    //Private constructor to prevent instantiation
    private CSVFileHandler() {
    }

    /**
     * This method reads and handles the content of a file that is passed as
     * a parameter
     *
     * @param filename, the path of the file
     * @return Army, an army object
     * @throws IOException,                   if the file does not exist
     * @throws NumberFormatException,         if a String cannot be parsed to an integer
     * @throws NullPointerException,          if the file is not found
     */
    public static Army readCSVArmy(String filename) throws IOException, NumberFormatException, NullPointerException {
        String armyName = null;
        boolean isAUnit = true;
        List<Unit> unitsFromFile = new ArrayList<>();

        Path path = Path.of(filename);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String lineOfText;
            int iterations = 0;
            while ((lineOfText = reader.readLine()) != null) {
                int i = 0;
                String[] words = lineOfText.split(",");
                Iterator<String> it = Arrays.stream(words).iterator();
                while (it.hasNext()) {
                    words[i] = it.next().strip();
                    i++;
                }
                if (iterations == 0) {
                    armyName = words[0];
                } else {
                    String armyType = words[0];
                    String unitName = words[1];
                    String unitHealth = words[2];
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
        }
        if (!isAUnit)
            throw new IllegalArgumentException("Please enter a legal unit type");

        return new Army(armyName, unitsFromFile);
    }

    /**
     * This method writes to a file
     *
     * @param army,     an army of units
     * @param filename, a file where the units will be listed
     * @throws IOException, if the file does not exist
     */
    public static void writeCSVArmy(Army army, String filename) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {
            writer.write(army.getName() + "\n");
            for (Unit unit : army.getUnitList()) {
                writer.write(unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
            }
        }
    }

    /**
     * This method reads and handles the content of a file that is passed as
     * a parameter
     *
     * @param filename, the path of the file
     * @return String, path to a file
     * @throws IOException, if the file does not exist
     */
    public static String readCSVArmyPath(String filename) throws IOException {
        Path path = Path.of(filename);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String lineOfText;
            if ((lineOfText = reader.readLine()) != null) {
                return lineOfText;
            }
        }
        return null;
    }

    /**
     * This method writes to a csv file
     *
     * @param filename, a file where the units will be listed
     * @throws IOException, if the file does not exist
     */
    public static void writeCSVArmyPath(String filename, String content) throws IOException {
        if (content!=null) {
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename).toAbsolutePath())) {
                if (!content.isBlank()) {
                    String contentToBeWritten = Path.of(content).toAbsolutePath().toString();
                        writer.write(contentToBeWritten + "\n");
                }
            }
        }
    }
}
