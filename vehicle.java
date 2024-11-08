/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameproject;

/**
 *
 * @author USER
 */
//import javax.swing.*;
import java.awt.*;


abstract class Vehicle {
    protected int x, y;
    protected int speed;

    public Vehicle(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void move() {
        x -= speed; 
    }

    public abstract void draw(Graphics g);
    
    
    public abstract int getWidth();
    public abstract int getHeight();
}





class Car extends Vehicle {
    public Car(int x, int y) {
        super(x, y);
        this.speed = 15;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 80, 30);
        g.fillRect(x + 15, y - 20, 50, 20);
        g.setColor(Color.BLACK);
        g.fillOval(x + 10, y + 25, 20, 20);
        g.fillOval(x + 50, y + 25, 20, 20);
    }

    @Override
    public int getWidth() {
        return 80;
    }

    @Override
    public int getHeight() {
        return 30;
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(int x, int y) {
        super(x, y);
        this.speed = 30;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 50, 10);
        g.fillRect(x + 10, y - 10, 30, 10);
        g.setColor(Color.BLACK);
        g.fillOval(x + 5, y + 10, 15, 15);
        g.fillOval(x + 30, y + 10, 15, 15);
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 20;
    }
}

class PCar extends Vehicle {
    public PCar(int x, int y) {
        super(x, y);
        this.speed = 15;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 80, 30);
        g.fillRect(x + 15, y - 20, 50, 20);
        g.setColor(Color.BLACK);
        g.fillOval(x + 10, y + 25, 20, 20);
        g.fillOval(x + 50, y + 25, 20, 20);
    }

    @Override
    public int getWidth() {
        return 80;
    }

    @Override
    public int getHeight() {
        return 30;
    }
}
