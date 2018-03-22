package com.groupproject.snake;

import sofia.util.Random;
import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 * This Test class is used to make sure that our model for the Snake game will
 * correctly work, from the getter to the setter methods. The methods for the
 * move is also checked to make sure that each case for where the snake is
 * moving is updated in the deque
 *
 * @author Brannon
 * @version Dec 2, 2013
 */

public class SnakeTest
    extends TestCase
{
    private Snake snake;


    // ----------------------------------------------------------
    /**
     * Creates a new Grid that is 10x10, and makes sure that the Randomly
     * spawned fruit starts at 2 above the snake's head
     */
    public void setUp()
    {
        Random.setNextInts(5, 3, 6, 3, 6, 4);
        snake = new Snake(10);
    }


    // ----------------------------------------------------------
    /**
     * Tests the method that will return the x-value on the grid for the
     * original fruit, being at (5, 3)
     */
    public void testGetFruitLocation()
    {
        assertEquals(new Location(5, 3), snake.getFruitLocation());
    }


    // ----------------------------------------------------------
    /**
     * Tests the method to make sure that the Deque that is returned will have
     * the correction Location values for both their y's and x's
     */
    public void testGetSnake()
    {
        DoublyLinkedDeque<Location> nakedsnake = snake.getSnake();
        Location head = nakedsnake.frontItem();
        Location tail = nakedsnake.rearItem();
        assertEquals(5, head.x());
        assertEquals(5, head.y());
        assertEquals(5, tail.x());
        assertEquals(6, tail.y());
    }


    // ----------------------------------------------------------
    /**
     * Tests the method that will set the cell to one of the enumerated snake
     * cell types
     */
    public void testSetCell()
    {
        snake.setCell(3, 3, SnakeCell.WALL);
        assertEquals(SnakeCell.WALL, snake.getCell(3, 3));
        snake.setCell(3, 3, SnakeCell.FIELD);
        assertEquals(SnakeCell.FIELD, snake.getCell(3, 3));
        snake.setCell(3, 3, SnakeCell.FRUIT);
        assertEquals(SnakeCell.FRUIT, snake.getCell(3, 3));
        snake.setCell(3, 3, SnakeCell.SNAKE);
        assertEquals(SnakeCell.SNAKE, snake.getCell(3, 3));
    }


    // ----------------------------------------------------------
    /**
     * Tests the method that will return the enumerated type of the snake cell
     * and making sure that each type will be returned
     */
    public void testGetCell()
    {
        assertEquals(SnakeCell.WALL, snake.getCell(0, 0));
        assertEquals(SnakeCell.SNAKE, snake.getCell(5, 5));
        assertEquals(SnakeCell.FIELD, snake.getCell(5, 4));
        assertEquals(SnakeCell.FRUIT, snake.getCell(5, 3));

    }


// /**
// * Tests the move method, where the integers entered will update the deque
// * holding the locations of all the snake cells Tests moving, eating, and
// * checking that the game is lost
// */
// public void testMove()
// {
// snake.move(5, 4);
// DoublyLinkedDeque<Location> solidsnake = snake.getSnake();
// Location head = solidsnake.frontItem();
// Location tail = solidsnake.rearItem();
// assertEquals(4, head.y());
// assertEquals(5, tail.y());
// snake.move(5, 3);
// DoublyLinkedDeque<Location> bigboss = snake.getSnake();
// Location head1 = bigboss.frontItem();
// Location tail1 = bigboss.rearItem();
// assertEquals(3, head1.y());
// assertEquals(5, tail1.y());
// assertEquals(3, snake.getSnakeSize());
// snake.move(5, 0);
// assertTrue(snake.isLost());
// }

    // ----------------------------------------------------------
    /**
     * tests getter for grid size
     */
    public void testGetGridSize()
    {
        assertEquals(10, snake.getGridSize());
    }


    // ----------------------------------------------------------
    /**
     * Tests that the snake will move in the direction of Up.
     */
    public void testMoveInDirectionDefaultAndN()
    {
        Location snakeHead = snake.getSnake().frontItem();
        snake.moveInDirection();
        assertEquals(snakeHead, snake.getSnake().frontItem());

        snake.setSnakeMotionDir(Direction.north);
        snake.moveInDirection();
        Location expected = new Location(snakeHead.x(), snakeHead.y() - 1);
        assertEquals(expected, snake.getSnake().frontItem());

// Random.setNextInts(4, 3);

// snake.moveInDirection();
// assertEquals(3, snake.getSnakeSize());
// assertEquals(3, snake.getSnake().frontItem().y());
// snake.setVertical(false);
// Random.setNextInts(3, 3);
// snake.moveInDirection();
// Random.setNextInts(3, 4);
// snake.moveInDirection();
// assertEquals(5, snake.getSnakeSize());
// assertEquals(3, snake.getSnake().frontItem().x());
// snake.setVertical(true);
// snake.setPositive(true);
// Random.setNextInts(4, 4);
// snake.moveInDirection();
// assertEquals(6, snake.getSnakeSize());
// assertEquals(3, snake.getSnake().frontItem().x());
// snake.setVertical(false);
// snake.setPositive(true);
// assertFalse(snake.isLost());
// snake.moveInDirection();
// snake.moveInDirection();
// assertTrue(snake.isLost());
    }


    // ----------------------------------------------------------
    /**
     * tests when the snake moves east
     */
    public void testMoveInDirectionE()
    {
        Location snakeHead = snake.getSnake().frontItem();
        snake.setSnakeMotionDir(Direction.east);
        snake.moveInDirection();
        Location expected = new Location(snakeHead.x() + 1, snakeHead.y());
        assertEquals(expected, snake.getSnake().frontItem());
    }


    // ----------------------------------------------------------
    /**
     * tests when the snake moves west and south
     */
    public void testMoveInDirectionWandS()
    {
        Location snakeHead = snake.getSnake().frontItem();
        snake.setSnakeMotionDir(Direction.west);
        snake.moveInDirection();
        Location expected = new Location(snakeHead.x() - 1, snakeHead.y());
        assertEquals(expected, snake.getSnake().frontItem());

        snake.setSnakeMotionDir(Direction.south);
        snake.moveInDirection();
        Location expected2 = new Location(expected.x(), expected.y() + 1);
        assertEquals(expected2, snake.getSnake().frontItem());
    }


    // ----------------------------------------------------------
    /**
     * tests when the snake eats a fruit
     */
    public void testSnakeMoveFruitEaten()
    {
        Location snakeHead = snake.getSnake().frontItem();

        snake.setSnakeMotionDir(Direction.north);
        assertFalse(snake.moveInDirection());
        assertTrue(snake.moveInDirection());

        assertEquals(3, snake.getSnakeSize());
        assertEquals(100, snake.getScore());
        assertEquals(
            SnakeCell.SNAKE,
            snake.getCell(snakeHead.x(), snakeHead.y() - 2));

        assertEquals(SnakeCell.FRUIT, snake.getCell(6, 3));

    }


    // ----------------------------------------------------------
    /**
     * tests when the snake moves and hits itself: game is lost. A 3-cell snake
     * cannot hit itself, snake must be at least 4-cell long.
     */
    public void testIsLostHitsItself()
    {
        snake.setSnakeMotionDir(Direction.north);
        assertFalse(snake.moveInDirection());
        assertTrue(snake.moveInDirection()); // size = 3 now

        snake.setSnakeMotionDir(Direction.east);
        assertTrue(snake.moveInDirection()); // size = 4 now

        snake.setSnakeMotionDir(Direction.south);
        assertTrue(snake.moveInDirection());
        assertFalse(snake.isLost());

        snake.setSnakeMotionDir(Direction.west);
        snake.moveInDirection();
        assertTrue(snake.isLost());

    }


    // ----------------------------------------------------------
    /**
     * game is lost because snake hit a wall
     */
    public void testIsLostHitsWall()
    {
        snake.setSnakeMotionDir(Direction.north);
        assertFalse(snake.moveInDirection());
        assertTrue(snake.moveInDirection()); // at (5, 3)
        assertFalse(snake.moveInDirection()); // at (5, 2)
        assertFalse(snake.moveInDirection()); // at (5, 1)

        assertFalse(snake.isLost());

        snake.moveInDirection();

        assertTrue(snake.isLost());

    }


    // ----------------------------------------------------------
    /**
     * Tests to make sure the snake size that is returned will be 2, as created
     * in the constructor
     */
    public void testGetSnakeSize()
    {
        assertEquals(2, snake.getSnakeSize());
    }


    // ----------------------------------------------------------
    /**
     * tests reset method
     */
    public void testResetModel()
    {
        Location oldHeadLocation = snake.getSnake().frontItem();
        snake.setSnakeMotionDir(Direction.north);
        snake.moveInDirection();
        snake.setGameLost(true);

        snake.resetModel();

        // fruit is at new next int
        assertEquals(new Location(6, 3), snake.getFruitLocation());

        // head is back to starX etc
        Location nowHeadLocation = snake.getSnake().frontItem();
        assertEquals(oldHeadLocation, nowHeadLocation);

        // move direction is back to "still"
        assertEquals(Direction.notMoving, snake.getSnakeMotionDir());

        // isLost is back to "false"
        assertFalse(snake.isLost());

        // score is set back to zero
        assertEquals(0, snake.getScore());

    }

//
// /**
// * Tests that the method that determines whether or not
// * the snake is moving vertical correctly will update
// */
//
// public void testSetVertical()
// {
// assertTrue(snake.isVertical());
// snake.setVertical(false);
// assertFalse(snake.isVertical());
// }
//
//
// /**
// * Tests that the method that determines whether or not
// * the snake is moving in a positive direction will
// * correctly update
// */
//
// public void testSetPositive()
// {
// assertFalse(snake.isPositive());
// snake.setPositive(true);
// assertTrue(snake.isPositive());
// }
//
//
// /**
// * Tests that the method correctly returns true in the
// * original case in determining if the snake is going up
// * or down.
// */
//
// public void testIsVertical()
// {
// assertTrue(snake.isVertical());
// }
//
//
// /**
// * Tests that the method will return false if the
// * snake is moving either down or to the right
// */
//
// public void testIsPositive()
// {
// assertFalse(snake.isPositive());
// }
//
//
// /**
// * Tests the method that the snake game is originally not
// * lost
// */
//
// public void testIsLost()
// {
// assertFalse(snake.isLost());
// }

}
