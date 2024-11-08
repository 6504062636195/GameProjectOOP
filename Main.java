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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public Main() {
        setTitle("Beware cars in road");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Beware cars in road", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Dropdown for lane selection
        JComboBox<String> laneSelect = new JComboBox<>(new String[] {"3 Lanes", "5 Lanes"});
        laneSelect.setSelectedIndex(1); // Default to 5 lanes

        // Dropdown for game mode selection
        JComboBox<String> modeSelect = new JComboBox<>(new String[] {"Endless Mode", "Target Distance 2500", "Target Distance 5000", "Target Distance 7500", "Target Distance 10000"});
        modeSelect.setSelectedIndex(0); // Default to Endless Mode

        JButton start = new JButton("Start Game");
        JButton exit = new JButton("Exit");

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        p2.add(new JLabel("Lane Count:"));
        p2.add(laneSelect);
        p3.add(new JLabel("Game Mode:"));
        p3.add(modeSelect);
        p1.add(p2, BorderLayout.WEST);
        p1.add(p3, BorderLayout.EAST);
        add(p1, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(start);
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int lanes = laneSelect.getSelectedIndex() == 0 ? 3 : 5;
                int targetDistance = getSelectedDistance(modeSelect.getSelectedIndex());
                boolean isEndlessMode = modeSelect.getSelectedIndex() == 0;
                setVisible(false);
                new GameFrame(lanes, isEndlessMode, targetDistance);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // Method to get selected distance from the dropdown
    private int getSelectedDistance(int index) {
        switch (index) {
            case 1: return 2500;
            case 2: return 5000;
            case 3: return 7500;
            case 4: return 10000;
            default: return Integer.MAX_VALUE; // Endless Mode
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
