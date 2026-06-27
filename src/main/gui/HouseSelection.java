package main.gui;

import javafx.application.Platform;
import javafx.stage.Stage;
import main.game.Main;

public class HouseSelection {

    public HouseSelection(Stage stage) {
        stage.close();
        Platform.exit();

        Thread gameThread = new Thread(Main::mainMenu);
        gameThread.setDaemon(false);
        gameThread.start();
    }
}
