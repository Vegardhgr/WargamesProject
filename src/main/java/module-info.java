module no.ntnu.idatg2001.wargames {
    requires javafx.controls;
    requires javafx.fxml;

    opens no.ntnu.idatg2001.wargames;
    opens no.ntnu.idatg2001.wargames.units;
    exports no.ntnu.idatg2001.wargames;
    exports no.ntnu.idatg2001.wargames.units;
    exports no.ntnu.idatg2001.wargames.utilities;
    opens no.ntnu.idatg2001.wargames.utilities;
    exports no.ntnu.idatg2001.wargames.app;
    opens no.ntnu.idatg2001.wargames.app;
    exports no.ntnu.idatg2001.wargames.controllers.createArmy;
    opens no.ntnu.idatg2001.wargames.controllers.createArmy;
    exports no.ntnu.idatg2001.wargames.controllers.mainScreen;
    opens no.ntnu.idatg2001.wargames.controllers.mainScreen;
    exports no.ntnu.idatg2001.wargames.controllers.simulateBattle;
    opens no.ntnu.idatg2001.wargames.controllers.simulateBattle;
    exports no.ntnu.idatg2001.wargames.app.viewArmyDetails;
    opens no.ntnu.idatg2001.wargames.app.viewArmyDetails;
}