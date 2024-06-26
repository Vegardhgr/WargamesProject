package no.ntnu.idatg2001.wargames.core.utilities;

import no.ntnu.idatg2001.wargames.core.units.*;
import no.ntnu.idatg2001.wargames.core.Army;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.fail;

class CSVFileHandlerTest {
    @Test
    void readCSVTest() {
        try {
            Army army = CSVFileHandler.readCSVArmy("files/testFiles/humanArmyTestFile.csv");
            Assertions.assertEquals("Human Army", army.getName());
            Assertions.assertEquals(13, army.getUnitList().size());
        } catch (IOException e) {
            fail("IOException because file does not exist.");
        }
    }

    @Test
    void readCSVFileNotFound() {
        String filename = "filename not existing";
        Assertions.assertThrows(IOException.class, () -> CSVFileHandler.readCSVArmy(filename));
    }

    @Test
    void writeCSVTest() {
        try {
            String filename = "files/testFiles/orcishHordeArmyTestFile.csv";
            List<Unit> units = new ArrayList<>();
            CSVFileHandler.writeCSVArmy(new Army("", units), filename);
            Assertions.assertEquals("", CSVFileHandler.readCSVArmy(filename).getName());
            Assertions.assertEquals(0, CSVFileHandler.readCSVArmy(filename).getUnitList().size());
            units.add(new CommanderUnit("Mountain King", 180));
            units.add(new InfantryUnit("Footman", 100));
            units.add(new CavalryUnit("Knight", 100));
            units.add(new RangedUnit("Archer", 100));
            CSVFileHandler.writeCSVArmy(new Army("Orcish Horde Army", units), filename);
            Army army = CSVFileHandler.readCSVArmy(filename);
            Assertions.assertEquals("Orcish Horde Army", army.getName());
            Assertions.assertEquals(4, army.getUnitList().size());
        } catch (IOException e) {
            fail("IOException because file does not exist.");
        }
    }
}