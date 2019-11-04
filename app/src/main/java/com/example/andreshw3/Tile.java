package com.example.andreshw3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class that describes a Tile object, which populates the GameGrid.
 * In the game 15 Squares, each Tile represents one of the filled squares.
 *
 * @author Andres Giesemann
 * @version November 2019
 */
public class Tile {

    private int x;
    private int y;
    private int value;
    private Paint tilePaint;
    static final int TILE_SIZE = 100;

    /**
     * Constructor for a Tile object, specifying its location on-screen and value.
     * @param x x-coordinate of its on-screen location
     * @param y y-coordinate of its on-screen location
     * @param value its unique integer value
     */
    public Tile(int x, int y, int value) {

        this.x = x;
        this.y = y;
        this.value = value;

        tilePaint = new Paint();
        tilePaint.setColor(Color.WHITE);
        tilePaint.setStyle(Paint.Style.STROKE);
        tilePaint.setTextSize(TILE_SIZE/2);

    }

    /**
     * Draws a visual representation of a Tile object, with a square border surrounding its value represented in text
     *
     * @param canvas pane upon which this Tile is drawn
     */
    public void drawTile(Canvas canvas) {

        canvas.drawRect(x, y, x + TILE_SIZE, y + TILE_SIZE, tilePaint);
        canvas.drawText("" + value, x + 15, y + 75, tilePaint);

    }

    /**
     * Setter method for the Tile's x-position on screen
     *
     * @param x new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter method for the Tile's y-position on screen
     *
     * @param y new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter method for the Tile's x-position on screen
     */
    public int getX() {
        return x;
    }

    /**
     * Getter method for the Tile's y-position on screen
     */
    public int getY() {
        return y;
    }

    /**
     * Getter method for the Tile's value
     */
    public int getValue() {
        return value;
    }
}
