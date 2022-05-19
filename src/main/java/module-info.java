module no.ntnu.idatg2001.wargames {
    requires javafx.controls;
    requires javafx.fxml;

    opens no.ntnu.idatg2001.wargames to javafx.fxml;
    opens no.ntnu.idatg2001.wargames.controllers to javafx.fxml;
    opens no.ntnu.idatg2001.wargames.units to javafx.fxml;
    exports no.ntnu.idatg2001.wargames;
    exports no.ntnu.idatg2001.wargames.controllers;
    exports no.ntnu.idatg2001.wargames.units;
    exports no.ntnu.idatg2001.wargames.utilities;
    opens no.ntnu.idatg2001.wargames.utilities to javafx.fxml;
    exports no.ntnu.idatg2001.wargames.app;
    opens no.ntnu.idatg2001.wargames.app to javafx.fxml;
}