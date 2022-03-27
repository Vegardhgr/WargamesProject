package no.ntnu.idatg2001.wargames.units;

import no.ntnu.idatg2001.wargames.units.units.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArmyFileHandler {
    private static String armyName;

    public static List<Unit> readCSV(String filename) throws IOException, NumberFormatException,
            ArrayIndexOutOfBoundsException {
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
                    if (words[0].equalsIgnoreCase("InfantryUnit")) {
                        unitsFromFile.add(new InfantryUnit(words[1], Integer.parseInt(words[2])));
                    } else if (words[0].equalsIgnoreCase("CavalryUnit")) {
                        unitsFromFile.add(new CavalryUnit(words[1], Integer.parseInt(words[2])));
                    } else if (words[0].equalsIgnoreCase("CommanderUnit")) {
                        unitsFromFile.add(new CommanderUnit(words[1], Integer.parseInt(words[2])));
                    } else if (words[0].equalsIgnoreCase("RangedUnit")) {
                        unitsFromFile.add(new RangedUnit(words[1], Integer.parseInt(words[2])));
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
        return unitsFromFile;
    }
}
