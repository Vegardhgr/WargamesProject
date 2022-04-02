package no.ntnu.idatg2001.wargames;

import no.ntnu.idatg2001.wargames.units.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ArmyFileHandlerTest {
    @Test
    void readCSVTest() throws IOException {
        ArmyFileHandler.readCSV("src/humanArmy.csv");
    }

    @Test
    void writeCSVTest() throws IOException {
        List<Unit> units = new ArrayList<>();
        units.add(new CommanderUnit("Mountain King", 180));
        units.add(new InfantryUnit("Footman", 100));
        units.add(new CavalryUnit("Knight", 100));
        units.add(new RangedUnit("Archer", 100));
        ArmyFileHandler.writeCSV(new Army("Orcish Horde Army", units), "src/orcishHordeArmy.csv");
    }
}