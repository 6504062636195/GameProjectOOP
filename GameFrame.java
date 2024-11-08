/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameproject;

/**
 *
 * @author USER
 */

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(int lanes, boolean isEndlessMode, int targetDistance) {
        setTitle("Beware cars in road");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel(lanes, isEndlessMode, targetDistance);
        add(gamePanel);

        setVisible(true);
    }
}


