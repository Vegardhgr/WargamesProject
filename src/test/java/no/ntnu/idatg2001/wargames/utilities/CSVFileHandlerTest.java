package no.ntnu.idatg2001.wargames.utilities;

import no.ntnu.idatg2001.wargames.units.*;
import no.ntnu.idatg2001.wargames.utilities.Army;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class CSVFileHandlerTest {
    @Test
    void readCSVTest() throws IOException {
        Army army = CSVFileHandler.readCSVArmy("src/humanArmyTestFile.csv");
        Assertions.assertEquals("Human Army", army.getName());
        Assertions.assertEquals(14, army.getUnitList().size());
    }

    @Test
    void readCSVFileNotFound() {
        String filename = "filename not existing";
        Assertions.assertThrows(IOException.class, () -> CSVFileHandler.readCSVArmy(filename));
    }

    @Test
    void writeCSVTest() throws IOException {
        String filename = "src/orcishHordeArmyTestFile.csv";
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
    }
}