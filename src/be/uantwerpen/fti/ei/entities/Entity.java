package be.uantwerpen.fti.ei.entities;

import java.awt.*;

/**
 * basic object from which other game objects inherit
 */
public class Entity {

    public int xCoordinate;
    public int yCoordinate;
    public int width;
    public int height;

    /**
     * sets object position
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPosition(int x, int y) {
        this.xCoordinate =x;
        this.yCoordinate =y;
    }

    /**
     * sets size of object
     * @param width width of object
     * @param height height of object
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }

    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

}
