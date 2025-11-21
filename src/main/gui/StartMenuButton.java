package main.gui;

import java.awt.*;
import javax.swing.*;

public class StartMenuButton extends JButton{
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 50;
    Font myFont = new Font("Consolas", Font.BOLD, 20);

    public StartMenuButton(String text){
        super(text);
        setFont(myFont);
        setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    }   
}
