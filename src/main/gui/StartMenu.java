package main.gui;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame {

    public StartMenu(String title) {
        super(title);

        // get the image
        ImageIcon startImageIcon = new ImageIcon(
            getClass().getClassLoader().getResource("main/gui/resources/startmenubg2.jpg")
        );

        // if image cannot be fetched
        if (startImageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Error: Background image failed to load!");
        }

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(startImageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());

        // Create buttons panel to hold the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(400, 500));

        // create buttons
        JButton playButton = new StartMenuButton("Play");
        JButton exitButton = new StartMenuButton("Exit");

        playButton.addActionListener(event -> new HouseSelection());

        // Add buttons to buttons panel
        buttonsPanel.add(playButton);
        buttonsPanel.add(exitButton);

        backgroundPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(backgroundPanel);

        // Fill the entire screen dynamically
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true);
    }   
}
