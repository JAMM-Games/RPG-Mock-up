package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Wyndel");

        // Create an instance of GamePanel and add it to the JFrame
        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);

        window.pack(); // Adjusts the window to fit the preferred size of the GamePanel set in its constructor

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamepanel.startGameThread();
    }

}
