package main;

import javax.swing.*;

public class Main {

    public static JFrame window; // Declare the JFrame as a static variable

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Wyndel");

        // Create an instance of GamePanel and add it to the JFrame
        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);

        window.pack(); // Adjusts the window to fit the preferred size of the GamePanel set in its constructor

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamepanel.setupGame(); // Set up the game, including assets and objects
        gamepanel.startGameThread();
    }

}
