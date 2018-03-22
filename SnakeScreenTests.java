package com.groupproject.snake;

import android.widget.TextView;
import sofia.util.Random;
import android.widget.Button;
import sofia.graphics.ShapeView;

/**
 * This class is for testing the screen class of our app.
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version Dec 4, 2013
 */
public class SnakeScreenTests
    extends student.AndroidTestCase<SnakeScreen>
{
    // data fields
    private Snake     testSnake;
    private ShapeView shapeView;
    private int       cellSize;
    private Button    start;
    private Button    reset;
    private TextView  scoreText;
    private TextView  highScoreText;
    private TextView  gameOver;


//
//
    // ----------------------------------------------------------
    // constructor
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public SnakeScreenTests()
    {
        super(SnakeScreen.class);

    }


    // ----------------------------------------------------------
    // set up
    /**
     * Initializes the test fixtures.
     */
    public void setUp()
    {
        Random.setNextInts(7, 3, 7, 3, 1, 2);

        testSnake = getScreen().getSnake();
        float viewSize = Math.min(shapeView.getWidth(), shapeView.getHeight());
        cellSize = (int)(viewSize / testSnake.getGridSize());

        start = getView(Button.class, R.id.start);
        reset = getView(Button.class, R.id.reset);
        scoreText = getView(TextView.class, R.id.scoreText);
        highScoreText = getView(TextView.class, R.id.highScoreText);
        gameOver = getView(TextView.class, R.id.gameOver);

    }


    // ----------------------------------------------------------
    // ***********************************************************
    // *** Private helper methods ***
    // ***********************************************************
    /**
     * Simulates touching down on the middle of the specified cell in the maze.
     */
    private void touchDownCell(int x, int y)
    {
        touchDown(shapeView, (x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    /**
     * Simulates moving the finger instantaneously to the middle of the
     * specified cell in the maze.
     */
    private void touchMoveCell(int x, int y)
    {
        touchMove((x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    /**
     * Simulates clicking the middle of the specified cell in the maze. This is
     * equivalent to calling: touchDownCell(x, y); touchUp();
     */
    private void clickCell(int x, int y)
    {
        touchDownCell(x, y);
        touchUp();
    }


    // ----------------------------------------------------------
    // ***********************************************************
    // *** Test methods ***
    // ***********************************************************
    /**
     * Tests startClicked method. Note that fruit will be located at (4, 1),
     * snake head is at (size/2, size/2) location in the beginning, and default
     * direction for snake move will be upward (north).
     */
    public void testStartClicked()
    {
        getScreen().resetUpdateHappened();
        click(start);

        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }
        // snakeDirection must be north
        assertEquals(Direction.north, getScreen().getSnakeMotionDir());

    }


    // ----------------------------------------------------------
    /**
     * Tests whether the snake changes direction when the user clicks a cell
     * west of the current direction of motion. The reset method is placed
     * before each move and the while loop before the assertion to make sure
     * that we wait until the update has happened
     */
    public void testTouchDown()
    {
        getScreen().resetUpdateHappened();
        click(start);

        // --------entering case north----------------
        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }

        assertEquals(Direction.north, getScreen().getSnakeMotionDir());
        getScreen().resetUpdateHappened();

        // --------entering case north--------------------
        clickCell(2, 6);
        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }

        assertEquals(Direction.west, getScreen().getSnakeMotionDir());

        // ----------entering case west------------------
        getScreen().resetUpdateHappened();
        clickCell(6, 10);
        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }
        assertEquals(Direction.south, getScreen().getSnakeMotionDir());

        // ------------entering case South---------------
        getScreen().resetUpdateHappened();
        clickCell(10, 10);
        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }
        assertEquals(Direction.east, getScreen().getSnakeMotionDir());

        // -------------entering case east--------------
        getScreen().resetUpdateHappened();
        clickCell(10, 1);
        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }
        assertEquals(Direction.north, getScreen().getSnakeMotionDir());

    }


    // ----------------------------------------------------------
    /**
     * Tests whether the snake changes direction when the user clicks a cell
     * west of the current direction of motion.
     */
    public void testTouchDownDefault()
    {

        assertEquals(Direction.notMoving, getScreen().getSnakeMotionDir());

        clickCell(6, 6);

        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }
        assertEquals(Direction.notMoving, getScreen().getSnakeMotionDir());

    }


    // ----------------------------------------------------------
    /**
     * Tests whether the snake changes direction when the user clicks a cell
     * west of the current direction of motion.
     */
    public void testTouchDownInvalidCell()
    {

        click(start);
        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }

        clickCell(5, 40);

        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }
        assertEquals(Direction.north, getScreen().getSnakeMotionDir());

    }


    // ----------------------------------------------------------
    /**
     * Tests whether the snake changes direction when the user clicks a cell
     * west of the current direction of motion.
     */
    public void testTouchDownAfterLost()
    {

        click(start);

        assertEquals(Direction.north, getScreen().getSnakeMotionDir());
        while (!getScreen().isLost())
        {
            try
            {
                Thread.sleep(7000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for lost");
        }

        // to include code coverage for "gameOver text field"
        assertEquals("GAME OVER", gameOver.getText());
        clickCell(10, 7);
        assertEquals(Direction.north, testSnake.getSnakeMotionDir());

    }


    // ----------------------------------------------------------
    /**
     * Tests fruit eating: both case -- one where high score increases and other
     * where it doesn't
     */
    public void testMoveAndUpdateEatFruit()
    {

        getScreen().resetUpdateHappened();
        click(reset);

        assertEquals(new Location(7, 3), testSnake.getFruitLocation());

        getScreen().resetUpdateHappened();
        click(start);
        while (!getScreen().isLost())
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }

        assertEquals(4, testSnake.getSnakeSize());
        assertEquals("Score: 100", scoreText.getText());
        assertEquals("High Score: 100", highScoreText.getText());

        getScreen().resetUpdateHappened();
        click(reset);

        getScreen().resetUpdateHappened();
        click(start);
        while (!getScreen().isLost())
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }

        assertEquals("High Score: 100", highScoreText.getText());

    }


    // ----------------------------------------------------------
    /**
     * Tests whether the reset button does what we want
     */
    public void testResetClicked()
    {

        click(start);

        getScreen().resetUpdateHappened();
        clickCell(10, 1);
        while (getScreen().getUpdateHappened() == 0)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                // empty block documented
            }
            System.out.println("Waiting for updateHappened");
        }

        click(reset);
        // snake is still, fruit is at (4, 1)
        assertEquals(Direction.notMoving, getScreen().getSnakeMotionDir());

        // snake head is at (7, 7), length 3
        assertEquals(new Location(7, 7), testSnake.getSnake().frontItem());
        assertEquals(3, testSnake.getSnakeSize());
        assertEquals(new Location(7, 3), testSnake.getFruitLocation());

    }


    // ----------------------------------------------------------
    /**
     * testing onTouchMove
     */
    public void testOnTouchMove()
    {
        getScreen().resetUpdateHappened();
        clickCell(4, 4);
        touchDownCell(4, 5);
        touchUp();
        assertEquals(Direction.notMoving, getScreen().getSnakeMotionDir());

    }
}
