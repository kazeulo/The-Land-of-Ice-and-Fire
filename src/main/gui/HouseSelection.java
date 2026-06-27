package main.gui;

import main.game.Main;

import javax.swing.*;
import java.awt.*;

public class HouseSelection extends JPanel {

    public HouseSelection() {
        // Close all open Swing windows (i.e. the StartMenu) before starting the game
        for (Window window : Window.getWindows()) {
            window.dispose();
        }

        // Run the console game on a non-daemon thread so the JVM stays alive
        Thread gameThread = new Thread(Main::mainMenu);
        gameThread.setDaemon(false);
        gameThread.start();
    }
}
