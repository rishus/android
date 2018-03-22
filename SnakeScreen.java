package com.groupproject.snake;

import sofia.graphics.RectangleShape;
import android.widget.TextView;
import sofia.app.ShapeScreen;
import android.app.Activity;
import android.os.Handler;

// -------------------------------------------------------------------------
/**
 * This class controls the screen for our app.
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version Dec 8, 2013
 */
public class SnakeScreen
    extends ShapeScreen
{

    // data fields
    // instance variables for the model
    private static final int   numCells       = 15;
    private Snake              theSnake;

    // instance variables for display
    private RectangleShape[][] mySnakeTiles;
    private float              cellLength;
    private TextView           scoreText;
    private TextView           highScoreText;
    private int                highScore      = 0;
    private TextView           gameOver;

    // instance variables for the timer
    private SnakeMotionControl cmc;
    private int                check;

    // instance variable for testing; to avoid race condition between update and
    // assert
    private int                updateHappened = 0;

    // ----------------------------------------------------------
    private class SnakeMotionControl
        extends Activity
    {
        Handler  timerHandler  = new Handler();

        Runnable timerRunnable = new Runnable() {
                                   public void run()
                                   {
                                       moveAndUpdate();
                                       if (!theSnake.isLost())
                                       {
                                           timerHandler.postDelayed(this, 2000);
                                       }
                                   }
                               };

    }

    // ----------------------------------------------------------
    /**
     * initializes the display
     */
    public void initialize()
    {
        // initialize model
        theSnake = new Snake(numCells);
        cmc = new SnakeMotionControl();

        // initialize display
        float deviceSize = Math.min(this.getWidth(), this.getHeight());
        cellLength = (int)(deviceSize / numCells);
        float cellWidth = cellLength;
        float cellHeight = cellLength;
        scoreText.setText("Score: " + theSnake.getScore());
        highScore = 0;
        highScoreText.setText("High Score: " + highScore);
        gameOver.setText("");

        mySnakeTiles = new RectangleShape[numCells][numCells];
        check = 5;
        for (int i = 0; i < numCells; i++)
        {
            for (int j = 0; j < numCells; j++)
            {
                RectangleShape tile =
                    new RectangleShape(i * cellWidth, j * cellHeight, (i + 1)
                        * cellWidth, (j + 1) * cellHeight);
                if (theSnake.getCell(i, j) == SnakeCell.WALL)
                {
                    tile.setImage("wall");

                }
                else if (theSnake.getCell(i, j) == SnakeCell.SNAKE)
                {
                    tile.setImage("snake");

                }
                else if (theSnake.getCell(i, j) == SnakeCell.FRUIT)
                {
                    tile.setImage("fruit");
                }
                else
                {
                    tile.setImage("grass");
                }

                this.add(tile);
                mySnakeTiles[i][j] = tile;

            }
        }

    }


    // public methods
    // ----------------------------------------------------------
    /**
     * responds when start button is clicked
     */
    public synchronized void startClicked()
    {
        theSnake.setSnakeMotionDir(Direction.north);
        cmc.timerHandler.postDelayed(cmc.timerRunnable, 0);
        ++updateHappened;
    }


    // ----------------------------------------------------------
    /**
     * responds when reset button is clicked
     */
    public synchronized void resetClicked()
    {
        cmc.timerHandler.removeCallbacks(cmc.timerRunnable);
//        check = 5;
        theSnake.resetModel();
        scoreText.setText("Score: " + theSnake.getScore());
        gameOver.setText("");
        for (int i = 0; i < numCells; i++)
        {
            for (int j = 0; j < numCells; j++)
            {
                RectangleShape tile = mySnakeTiles[i][j];

                if (theSnake.getCell(i, j) == SnakeCell.WALL)
                {
                    tile.setImage("wall");
                }
                else if (theSnake.getCell(i, j) == SnakeCell.SNAKE)
                {
                    tile.setImage("snake");
                }
                else if (theSnake.getCell(i, j) == SnakeCell.FRUIT)
                {
                    tile.setImage("fruit");
                }
                else
                {
                    tile.setImage("grass");

                }

            }
        }

    }


    // ----------------------------------------------------------
    /**
     * responds when user clicks on the screen
     *
     * @param x
     *            x-coordinate of the click location
     * @param y
     *            y-coordinate of the click location
     */
    public synchronized void onTouchDown(float x, float y)
    {
        cmc.timerHandler.removeCallbacks(cmc.timerRunnable);

        boolean actuallyProcess = processTouch(x, y);

        if (actuallyProcess)
        {
            moveAndUpdate();
            cmc.timerHandler.postDelayed(cmc.timerRunnable, 0);
        }
        ++updateHappened;
    }


    // ----------------------------------------------------------
    /**
     * responds when user clicks and moves on the screen
     *
     * @param x
     *            x-coordinate of the click location
     * @param y
     *            y-coordinate of the click location
     */
    public synchronized void onTouchMove(float x, float y)
    {
        cmc.timerHandler.removeCallbacks(cmc.timerRunnable);

        boolean actuallyProcess = processTouch(x, y);

        if (actuallyProcess)
        {
            moveAndUpdate();
            cmc.timerHandler.postDelayed(cmc.timerRunnable, 0);
        }
        ++updateHappened;

    }

    // ----------------------------------------------------------
    // private (helper) methods
    /**
     * This method responds when the user clicks anywhere on the field. The
     * snake is NOT allowed to move "into itself". So ... if the click is at a
     * point right behind the snake, now change is made. Otherwise, the
     * "direction of motion" of the snake from this time onwards. (Note that,
     * the click changes the relevant data field in the model. No change is made
     * on the screen directly here.)
     */
    private boolean processTouch(float newX, float newY)
    {
//        System.out.println("processTouch called");
        boolean actuallyprocess = false;

        if (theSnake.isLost())
        {
            return actuallyprocess;
        }
        int touchXCoord =
            (int)(newX / (Math.min(getWidth(), getHeight()) / numCells));
        int touchYCoord =
            (int)(newY / (Math.min(getWidth(), getHeight()) / numCells));

        if ((touchXCoord < 0) || (touchYCoord < 0)
            || (touchXCoord > theSnake.getGridSize() - 1)
            || (touchYCoord > theSnake.getGridSize() - 1))
        {
//            System.out
//                .println("processTouch called: click is outside the grid");
            return actuallyprocess;
        }

        Location head = theSnake.getSnake().frontItem();
        int headX = head.x();
        int headY = head.y();

        switch (theSnake.getSnakeMotionDir())
        {
            case north:
                if (touchXCoord < headX)
                {
                    theSnake.setSnakeMotionDir(Direction.west);
                }
                else if (touchXCoord > headX)
                {
                    theSnake.setSnakeMotionDir(Direction.east);
                }

                actuallyprocess = true;
//                System.out.println("new direction of motion is "
//                    + theSnake.getSnakeMotionDir());
                break;

            case south:
                if (touchXCoord < headX)
                {
                    theSnake.setSnakeMotionDir(Direction.west);
                }
                else if (touchXCoord > headX)
                {
                    theSnake.setSnakeMotionDir(Direction.east);
                }

                actuallyprocess = true;
//                System.out.println("new direction of motion is "
//                    + theSnake.getSnakeMotionDir());

                break;

            case east:
                if (touchYCoord < headY)
                {
                    theSnake.setSnakeMotionDir(Direction.north);
                }
                else if (touchYCoord > headY)
                {
                    theSnake.setSnakeMotionDir(Direction.south);
                }

                actuallyprocess = true;
//                System.out.println("new direction of motion is "
//                    + theSnake.getSnakeMotionDir());
                break;

            case west:
                if (touchYCoord < headY)
                {
                    theSnake.setSnakeMotionDir(Direction.north);
                }
                else if (touchYCoord > headY)
                {
                    theSnake.setSnakeMotionDir(Direction.south);
                }

                actuallyprocess = true;
//                System.out.println("new direction of motion is "
//                    + theSnake.getSnakeMotionDir());
                break;

            default:
//                System.out
//                    .println("defaut case for switch in processTouch called");
                break;

        }

        return actuallyprocess;

    }

    // ----------------------------------------------------------
    /**
     * This is the method that is called repeatedly be the timer. It invokes the
     * snake to move and update the screen to display the new situation.
     */
    private synchronized void moveAndUpdate()
    {
//        System.out.println("check=" + check);
//        ++check;

        // save old tail location: that will be changed
        Location oldTailLocation = theSnake.getSnake().rearItem();
//        Location oldHeadLocation = theSnake.getSnake().frontItem();

        // move the snake
        boolean isFruitEaten = theSnake.moveInDirection();

        if (theSnake.isLost())
        {
            // update the display in text field
            gameOver.setText("GAME OVER");
            if (theSnake.getScore() > highScore)
            {
                highScore = theSnake.getScore();
                highScoreText.setText("High Score: " + highScore);
            }

            return;
        }

        // get new head location: that tile also needs to be changed
        Location newHeadLocation = theSnake.getSnake().frontItem();

        // update head,fruit,and, tail
//        System.out.println("updating head from=" + oldHeadLocation.toString()
//            + " to=" + newHeadLocation.toString());

        mySnakeTiles[newHeadLocation.x()][newHeadLocation.y()]
            .setImage("snake");

        if (isFruitEaten)
        {
//            System.out.println("fruit eaten, putting a new fruit");
            Location newFruitLocation = theSnake.getFruitLocation();
            mySnakeTiles[newFruitLocation.x()][newFruitLocation.y()]
                .setImage("fruit");
            // update the score displays
            scoreText.setText("Score: " + theSnake.getScore());

        }
        else
        {
//            System.out.println("updating tail");
//            System.out.println("fruit not eaten, snake moves");
            mySnakeTiles[oldTailLocation.x()][oldTailLocation.y()]
                .setImage("grass");
        }

    }


// public synchronized void checkTiles()
// {
// for (int i = 0; i < numCells; i++)
// {
// for (int j = 0; j < numCells; j++)
// {
// RectangleShape tile = mySnakeTiles[i][j];
// if (theSnake.getCell(i, j) == SnakeCell.WALL)
// {
// continue;
// }
//
// if (tile.getFillColor() == Color.green
// && theSnake.getCell(i, j) != SnakeCell.FIELD)
// {
// System.out.println("CASE 1: cell(" + i + ", " + j
// + "): color = " + tile.getFillColor() + " "
// + Color.green + "  , type = " + theSnake.getCell(i, j));
// }
// if (tile.getFillColor() != Color.green
// && theSnake.getCell(i, j) == SnakeCell.FIELD)
// {
// System.out.println("CASE 2: cell(" + i + ", " + j
// + "): color = " + tile.getFillColor() + " "
// + Color.green + "  , type = " + theSnake.getCell(i, j));
// }
// if (tile.getFillColor() == Color.yellow
// && theSnake.getCell(i, j) != SnakeCell.SNAKE)
// {
// System.out.println("CASE 3: cell(" + i + ", " + j
// + ") color = " + tile.getFillColor() + "  , type = "
// + theSnake.getCell(i, j));
// }
// if (tile.getFillColor() != Color.yellow
// && theSnake.getCell(i, j) == SnakeCell.SNAKE)
// {
// System.out.println("CASE 4: cell(" + i + ", " + j
// + ") color = " + tile.getFillColor() + "  , type = "
// + theSnake.getCell(i, j));
// }
// }
// }
//
// }

    // ----------------------------------------------------------
    /**
     * getter for the model
     *
     * @return the model
     */
    public Snake getSnake()
    {
        return theSnake;
    }


    // ----------------------------------------------------------
    /**
     * returns whether the game is lost or not
     *
     * @return true or false
     */
    public synchronized boolean isLost()
    {
        return theSnake.isLost();
    }


    // ----------------------------------------------------------
    /**
     * returns the direction in which the snake is currently set to move
     *
     * @return a direction N, S, E, W, "notMoving" if the snake is still
     */
    public synchronized Direction getSnakeMotionDir()
    {
        return theSnake.getSnakeMotionDir();
    }


    // ----------------------------------------------------------
    /**
     * this method is used while testing -- it makes sure that the assertions
     * are done after the updates have happened
     *
     * @return 0 if no update has happened yet, a positive number if one or more
     *         updates have happened
     */
    public synchronized int getUpdateHappened()
    {
        return updateHappened;
    }


    // ----------------------------------------------------------
    /**
     * this method is used while testing -- it is used for setting the
     * "updates happened" to 0 before starting a new thread
     */
    public synchronized void resetUpdateHappened()
    {
        updateHappened = 0;
    }

}
