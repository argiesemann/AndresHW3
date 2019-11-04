package com.example.andreshw3;

import android.graphics.Canvas;
import java.util.Random;

/**
 * Class GameGrid. This class contains the logic for playing
 * a game of 15 Squares. A GameGrid comprises Tile objects, each one
 * representing a "square" from 15 Squares.
 * 
 * @author Andres Giesemann
 * @version November 2019
 */
public class GameGrid {


    private Tile[][] tiles;
    private int size;
    private int leftBorder;
    private int topBorder;

    /**
     * Constructor. Makes GameGrid object with coordinates of its top-left point
     *
     * @param leftBorder
     * @param topBorder
     */
    public GameGrid(int leftBorder, int topBorder) {
        
        size = 4;
        tiles = new Tile[size][size];
        this.leftBorder = leftBorder;
        this.topBorder = topBorder;
        initializeTiles();

    }

    /**
     * Randomly fills the GameGrid with tiles for values 1 through size*size.
     * The tile with value 0 gets assigned null.
     *
     * To test the win-checking functionality, uncomment the code at the end of this method.
     */
    public void initializeTiles() {
        
        int count = 0;
        int vals[] = new int[size*size];

        //populate a 1D array with all the values for this game size
        for (int g = 0; g < vals.length; g++) {
            vals[g] = g;
        }
        
        shuffleArray(vals);

        //Creates tiles with random values
        //The tile with value zero is set to null
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j <tiles[i].length; j++) {
                
                if (vals[count] == 0) {
                    tiles[i][j] = null;
                    count++;
                }
                
                else if (count <= size*size) {
                    tiles[i][j] = new Tile(leftBorder + Tile.TILE_SIZE*i,topBorder + Tile.TILE_SIZE*j,vals[count]);
                    count++;
                }
            }
        }

        // uncomment the following to test win checking
        /*int winCount = 1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
            
                tiles[j][i] = new Tile(leftBorder + Tile.TILE_SIZE * j, topBorder + Tile.TILE_SIZE * i, winCount);
                winCount++;
            }
        }
        tiles[tiles.length-1][tiles.length-1] = null;*/
    }

    /**
     * Shuffles the values within an array. Used in the
     * initializeTiles() method
     *
     * @param a the array to be shuffled
     */
    private void shuffleArray(int[] a) {
        
        Random gen = new Random();
        int temp;
        int randPosition;

        //swaps the value at a[i] with value at a[randPosition]
        for (int i = 0; i < a.length; i++) {
            temp = a[i];
            randPosition = gen.nextInt(a.length);
            a[i] = a[randPosition];
            a[randPosition] = temp;
        }
    }

    /**
     * Resets the coordinates of each tile with proper values.
     * Used after a tile is placed to "snap" into position.
     */
    public void refreshTiles() {
        
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j <tiles[i].length; j++) {
                
                if (tiles[i][j] != null) {
                    
                    tiles[i][j].setX(leftBorder + Tile.TILE_SIZE * i);
                    tiles[i][j].setY(topBorder + Tile.TILE_SIZE * j);
                }
            }
        }
    }

    /**
     * Iterates through and draws the GameGrid's Tiles
     *
     * @param canvas pane upon which Tiles are drawn
     */
    public void drawTiles(Canvas canvas) {
        
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j <tiles[i].length; j++) {
                
                if (tiles[i][j] != null) {
                    tiles[i][j].drawTile(canvas);
                }
            }
        }
    }


    /**
     * Places a tile in its new position by finding the null tile
     * and swapping their positions. Doesn't check for validity because
     * that is handled when a Tile is selected.
     *
     * @param tile the Tile to be placed
     */
    public void placeTile(Tile tile) {
        
        int tileCoordI = -1;
        int tileCoordJ = -1;

        //first pass through the array to assign the null tile to placed tile
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j <tiles[i].length; j++) {
                
                if (tiles[i][j] == null) {
                    tiles[i][j] = tile;
                    tileCoordI = i;
                    tileCoordJ = j;
                }

            }
        }

        //second pass-through assigns the old position of placed tile to null
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j <tiles[i].length; j++) {
                
                if (tiles[i][j].getValue() == tile.getValue() && (tileCoordI != i || tileCoordJ != j)) {
                    tiles[i][j] = null;
                }
            }
        }
        refreshTiles();
    }

    /**
     * Makes the GameGrid larger by one Tile on each axis
     */
    public void increaseSize() {
        
        if (size < 8) {
            size++;
            tiles = new Tile[size][size];
            initializeTiles();
        }
    }


    /**
     * Makes the GameGrid smaller by one Tile on each axis
     */
    public void decreaseSize() {
        
        if (size >= 4) {
            size--;
            tiles = new Tile[size][size];
            initializeTiles();
        }
    }

    /**
     * Checks if it is legal to select a Tile at [i,j] by checking its
     * adjacent Tiles for the null Tile.
     *
     * @param i coordinate of the Tile
     * @param j coordinate of the Tile
     * @return true if this position is adjacent to the null Tile
     */
    private boolean isValid (int i, int j) {
        
        if (isEmpty(i-1, j) || isEmpty(i+1, j) || isEmpty(i, j-1) || isEmpty(i, j+1)) {
            return true;
        }
        
        return false;
    }

    /**
     * Checks if the GameGrid position described by [i,j] is the null Tile.
     *
     * @param i coordinate of a Tile
     * @param j coordinate of a Tile
     * @return true if [i,j] is a null Tile
     */
    private boolean isEmpty(int i, int j) {
        
        if (i < 0 || i >= tiles.length || j < 0 || j >= tiles[0].length) {
            return false;
        }

        return tiles[i][j] == null;
    }


    /**
     * Checks if the GameGrid's current configuration is a win.
     * This means that null Tile is in the bottom right corner and that
     * Tile values are increasing from left to right and top to bottom
     *
     * @return true if GameGrid is in a winning configuration
     */
    public boolean isWin() {
        
        if (tiles[0][0] != null) {
            
            int lastVal = tiles[0][0].getValue();
            
            if (lastVal == 1) {
                
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles.length; j++) {
                        
                        if  (!(i == tiles.length - 1 && j == tiles.length - 1) 
                            && (tiles[j][i] == null || tiles[j][i].getValue() < lastVal)) {
                            return false;
                        }
                        
                        if (!(i == tiles.length -1 && j == tiles.length - 1)) {
                            lastVal = tiles[j][i].getValue();
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Selects the tile at specified screen coordinates if a) there is a tile
     * at that location and b) that tile is adjacent to the null Tile
     *
     * @param x x-coordinate of a screen tap
     * @param y y-coordinate of a screen tap
     * @return the Tile specified by screen location (x,y)
     */
    public Tile getTileAt(int x, int y) {
        
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j <tiles[i].length; j++) {
                
                if (tiles[i][j] != null) {

                    //Checks for whether these coordinates are within the boundaries of the current tile[i][j]
                    if ((x > tiles[i][j].getX() && x < tiles[i][j].getX() + Tile.TILE_SIZE )
                        && (y > tiles[i][j].getY() && y < tiles[i][j].getY() + Tile.TILE_SIZE)) {
                        
                        if (isValid(i,j)) {
                            return tiles[i][j];
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * Getter method for the GameGrid's size
     *
     * @return this GameGrid's size
     */
    public int getSize() {
        return size;
    }
}
