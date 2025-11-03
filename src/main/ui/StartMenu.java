/*
 *  Class name: MainUI
 *  Description: GUI version of the game launcher.
 *  Author: KS Manejo
 */

package main.ui;

import javax.swing.*;

import main.game.GameBuilder;

import java.awt.*;

public class StartMenu extends JFrame {
	private static final long serialVersionUID = 1L;

    private GameBuilder gameBuilder = new GameBuilder();

    public StartMenu() {
        setTitle("Main Game Menu");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center window
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("The Land of Ice and Fire", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 23));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 40, 60));

        JButton playButton = new JButton("Play");
        JButton infoButton = new JButton("Game Info");
        JButton exitButton = new JButton("Exit");

        playButton.setFont(new Font("Poppins", Font.PLAIN, 18));
        infoButton.setFont(new Font("Poppins", Font.PLAIN, 18));
        exitButton.setFont(new Font("Poppins", Font.PLAIN, 18));

        buttonPanel.add(playButton);
        buttonPanel.add(infoButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Event listeners
        playButton.addActionListener(e -> {
            dispose(); // close menu window if needed
            gameBuilder.gameLoop();
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }
}
