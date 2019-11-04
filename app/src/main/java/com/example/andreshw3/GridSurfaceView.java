package com.example.andreshw3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Child class of SurfaceView for displaying a GameGrid object
 *
 * @author Andres Giesemann
 * @version November 2019
 */
public class GridSurfaceView extends SurfaceView {

    private Paint gridPaint;
    private Paint winPaint;
    private GameGrid gameGrid;
    private boolean win;
    
    //these define the boundaries of the Grid being drawn on screen
    private static final int LEFT_BORDER = 500;
    private static final int TOP_BORDER = 100;

    //bottom and right borders vary depending on user's selected game size
    private int bottomBorder;
    private int rightBorder;


    /**
     * Constructor. Initializes variables
     *
     * @param context Requirement from parent class
     * @param attrs Requirement from parent class
     */
    public GridSurfaceView(Context context, AttributeSet attrs) {
        
        super(context, attrs);
        
        gridPaint = new Paint();
        winPaint = new Paint();
        gridPaint.setColor(Color.WHITE);
        winPaint.setColor(Color.GREEN);
        winPaint.setTextSize(100);
        
        gameGrid =  new GameGrid(LEFT_BORDER, TOP_BORDER);

        bottomBorder = TOP_BORDER + Tile.TILE_SIZE * gameGrid.getSize();
        rightBorder = LEFT_BORDER + Tile.TILE_SIZE * gameGrid.getSize();
        
        win = gameGrid.isWin();
        
        setWillNotDraw(false);
        
    }

    /**
     * Draws GameGrid according to its size and win-status
     *
     * @param canvas Pane upon which GameGrid is drawn
     */
    public void onDraw(Canvas canvas) {

        bottomBorder = TOP_BORDER + Tile.TILE_SIZE * gameGrid.getSize();
        rightBorder = LEFT_BORDER + Tile.TILE_SIZE * gameGrid.getSize();

        //top line
        canvas.drawLine(LEFT_BORDER, TOP_BORDER, rightBorder, TOP_BORDER, gridPaint);

        //bottom line
        canvas.drawLine(LEFT_BORDER, bottomBorder, rightBorder, bottomBorder, gridPaint);

        //left line
        canvas.drawLine(LEFT_BORDER, TOP_BORDER, LEFT_BORDER, bottomBorder, gridPaint);

        //right line
        canvas.drawLine(rightBorder, TOP_BORDER, rightBorder, bottomBorder, gridPaint);

        gameGrid.drawTiles(canvas);

        win = gameGrid.isWin();

        if (win) {
            canvas.drawText("WINNER", LEFT_BORDER, 100, winPaint);
        }
    }

    /**
     * Getter method for the GameGrid object. Allows the Controller
     * to have a reference to the same GameGrid.
     *
     * @return the GameGrid object of this GridSurfaceView instance
     */
    public GameGrid getGameGrid() {
        return gameGrid;
    }

}
