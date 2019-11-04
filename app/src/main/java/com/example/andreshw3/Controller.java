package com.example.andreshw3;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Class that handles user interaction with this implementation of the
 * 15 Squares game. Handles screen-tapping and button-pushing
 *
 * @author Andres Giesemann
 * @version November 2019
 */
public class Controller implements View.OnTouchListener, View.OnClickListener {


    private GameGrid gameGrid;
    private GridSurfaceView gsv;
    private Tile selectedTile;

    /**
     * Constructor for a Controller
     * @param gameGrid the GameGrid to be controlled
     * @param gsv the GridSurfaceView which is listened to
     */
    public Controller(GameGrid gameGrid, GridSurfaceView gsv){

        this.gameGrid = gameGrid;
        this.gsv = gsv;
    }

    /**
     * Handles screen touches, differentiating between begun, continued, and terminated touches.
     * On a touch begun, instance variable selectedTile is assigned to the Tile at the touch's location.
     * On a touch continued, selectedTile's x and y coordinates are updated to according to the user's
     * dragging movement.
     * On a touch terminated, selectedTile snaps into place on the GameGrid
     *
     * @param v the View that sent this event
     * @param event the MotionEvent (begun, continued, or terminated touch)
     * @return true if the event is one of the three described touches
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /**
        External Citation
            Date:       22 October 2019
            Problem:    Could not determine whether user was starting, stopping, or
                        continuing a touch
            Resources:
                https://stackoverflow.com/questions/15862705/touch-events-and-gestures-in-surface-view
                https://developer.android.com/reference/android/view/MotionEvent.html
            Solution: I used the StackOverflow post as a guide for checking different types of touch events
         */
        if (event.getAction() == MotionEvent.ACTION_DOWN && selectedTile == null) {

            selectedTile = gameGrid.getTileAt((int)event.getX(), (int)event.getY());
            if (selectedTile != null) {
                selectedTile.setX((int) event.getX() - 50);
                selectedTile.setY((int) event.getY() - 50);
            }
            gsv.invalidate();
            return true;

        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            if (selectedTile != null) {
                selectedTile.setX((int) event.getX() - 50);
                selectedTile.setY((int) event.getY() - 50);
            }
            gsv.invalidate();
            return true;

        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (selectedTile != null) {
                gameGrid.placeTile(selectedTile);
            }
            selectedTile = null;
            gsv.invalidate();
            return true;

        }

        return false;
    }

    /**
     * Handles user interaction with the Buttons, allowing for reset and resizing functionality
     *
     * @param v the View that sent this event
     */
    @Override
    public void onClick(View v) {

        Button b = (Button)v;

        if (b.getText().toString().equals("Reset")) {
            gameGrid.initializeTiles();
        }

        else if (b.getText().toString().equals("Increment Size")){
            gameGrid.increaseSize();
        }

        else if (b.getText().toString().equals("Decrement Size")){
            gameGrid.decreaseSize();
        }

        gsv.invalidate();
    }
}
