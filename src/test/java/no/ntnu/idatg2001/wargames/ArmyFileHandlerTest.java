package no.ntnu.idatg2001.wargames;

import no.ntnu.idatg2001.wargames.units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ArmyFileHandlerTest {
    @Test
    void readCSVTest() throws IOException {
        Army army = ArmyFileHandler.readCSV("src/humanArmyTestFile.csv");
        Assertions.assertEquals("Human Army", army.getName());
        Assertions.assertEquals(1, army.getUnitList().size());
    }

    @Test
    void readCSVFileNotFound() {
        String filename = "filename not existing";
        Assertions.assertThrows(IOException.class, () -> ArmyFileHandler.readCSV(filename));
    }

    @Test
    void writeCSVTest() throws IOException {
        String filename = "src/orcishHordeArmyTestFile.csv";
        List<Unit> units = new ArrayList<>();
        ArmyFileHandler.writeCSV(new Army("", units), filename);
        Assertions.assertEquals("", ArmyFileHandler.readCSV(filename).getName());
        Assertions.assertEquals(0, ArmyFileHandler.readCSV(filename).getUnitList().size());
        units.add(new CommanderUnit("Mountain King", 180));
        units.add(new InfantryUnit("Footman", 100));
        units.add(new CavalryUnit("Knight", 100));
        units.add(new RangedUnit("Archer", 100));
        ArmyFileHandler.writeCSV(new Army("Orcish Horde Army", units), filename);
        Army army = ArmyFileHandler.readCSV(filename);
        Assertions.assertEquals("Orcish Horde Army", army.getName());
        Assertions.assertEquals(4, army.getUnitList().size());
    }
}