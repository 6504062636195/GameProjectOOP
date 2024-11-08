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
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GamePanel extends JPanel implements Runnable {

    private ArrayList<Vehicle> obstacles;
    private PCar playerCar;
    private boolean gameRunning = true;
    private Random random = new Random();

    private int laneHeight;
    private int laneCount;
    private int targetDistance;
    private int distanceTraveled = 0;
    private int currentLane = 0;
    private AtomicInteger activeLanes = new AtomicInteger(0);
    private long startTime;

    public GamePanel(int lanes, boolean isEndlessMode, int targetDistance) {
        this.laneCount = lanes;
        this.targetDistance = targetDistance;
        this.laneHeight = 100;

        obstacles = new ArrayList<>();
        playerCar = new PCar(100, (laneCount / 2) * laneHeight + laneHeight / 2 - 15);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP && currentLane > 0) {
                    currentLane--;
                    playerCar.y = currentLane * laneHeight + laneHeight / 2 - 15;
                } else if (key == KeyEvent.VK_DOWN && currentLane < laneCount - 1) {
                    currentLane++;
                    playerCar.y = currentLane * laneHeight + laneHeight / 2 - 15;
                }
            }
        });

        for (int i = 0; i < laneCount; i++) {
            new Thread(new LaneController(i)).start();
        }

        startTime = System.currentTimeMillis();
        new Thread(this).start();
    }

    private void returnToMainMenu() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.setVisible(false);
        new Main().setVisible(true);
    }

    private class LaneController implements Runnable {

        private int lane;
        private boolean hasVehicle = false;

        public LaneController(int lane) {
            this.lane = lane;
        }

        @Override
        public void run() {
            while (gameRunning) {
                if (!hasVehicle && activeLanes.get() < laneCount) {
                    int y = lane * laneHeight + laneHeight / 2 - 15;
                    int x = getWidth();

                    Vehicle newVehicle = random.nextBoolean() ? new Car(x, y) : new Motorcycle(x, y);

                    synchronized (obstacles) {
                        obstacles.add(newVehicle);
                        hasVehicle = true;
                        activeLanes.incrementAndGet();
                    }

                    while (newVehicle.x > 0 && gameRunning) {
                        newVehicle.move();
                        repaint();
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    synchronized (obstacles) {
                        obstacles.remove(newVehicle);
                        hasVehicle = false;
                        activeLanes.decrementAndGet();
                    }
                }

                try {
                    Thread.sleep(1000 + random.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        laneHeight = getHeight() / laneCount;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), laneHeight * laneCount);

        g.setColor(Color.WHITE);
        for (int i = 1; i <= laneCount; i++) {
            int laneY = i * laneHeight;
            g.drawLine(0, laneY, getWidth(), laneY);
        }

        playerCar.draw(g);
        synchronized (obstacles) {
            for (Vehicle vehicle : obstacles) {
                vehicle.draw(g);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Distance: " + distanceTraveled + (targetDistance == Integer.MAX_VALUE ? "" : "/" + targetDistance), 10, 20);
    }

    @Override
    public void run() {
        while (gameRunning) {
            synchronized (obstacles) {
                for (Vehicle vehicle : obstacles) {
                    if (checkCollision(playerCar, vehicle)) {
                        gameRunning = false;
                        JOptionPane.showMessageDialog(this, "Game Over!");
                        showReturnToMenu();
                        return;
                    }
                }
            }

            distanceTraveled += 1;
            if (distanceTraveled >= targetDistance) {
                gameRunning = false;
                JOptionPane.showMessageDialog(this, "You Win! Level Complete.");
                showReturnToMenu();
                return;
            }

            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showReturnToMenu() {
        int response = JOptionPane.showConfirmDialog(this, "Would you like to return to the main menu?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();  // Close the current game window
            new Main().setVisible(true);  // Return to the main menu
        } else {
            System.exit(0);  // Exit the game
        }
    }

    private boolean checkCollision(Vehicle car1, Vehicle car2) {
        Rectangle rect1 = new Rectangle(car1.x, car1.y, 80, 30);
        Rectangle rect2 = new Rectangle(car2.x, car2.y, 80, 30);
        return rect1.intersects(rect2);
    }
}
