package com.example.andreshw3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

/**
 * MainActivity for an Android implementation of the 15 Squares game.
 * User plays by clicking and dragging squares into empty spaces.
 *
 * @author Andres Giesemann
 * @version November 2019
 *
 * Enhancements:
 *  - dragging
 *  - dynamic game size, controlled by buttons
 *
 * Note:
 *  - to use win-checking functionality, uncomment the code in GameGrid's
 *    initializeTiles() as directed by the method header
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get references to all the objects on the layout and assign them
        //the same Controller for handling user interaction
        GridSurfaceView gsv = findViewById(R.id.gridSurfaceView);
        Controller controller = new Controller(gsv.getGameGrid(), gsv);
        gsv.setOnTouchListener(controller);

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(controller);

        Button increaseButton = findViewById(R.id.increaseSize);
        increaseButton.setOnClickListener(controller);

        Button decreaseButton = findViewById(R.id.decreaseSize);
        decreaseButton.setOnClickListener(controller);
    }
}
