package com.groupproject.snake;

import sofia.util.Random;

/**
 * This is the model for our app.
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version Nov 12, 2013
 */
public class Snake
{
    // Data fields
    private SnakeCell[][]               grid;
    private int                         numCells;
    private int                         startX;
    private int                         startY;
    private boolean                     isLost = false;
    private DoublyLinkedDeque<Location> snake;
    private int                         snakeSize;
// private boolean vertical;
// private boolean positive;
    private int                         fruitX;
    private int                         fruitY;
    private Direction                   snakeMotionDir;
    private int                         score;


    // Constructors
    // ----------------------------------------------------------
    /**
     * Creates a new Snake object.
     *
     * @param newGridSize
     *            number of cells in the grid
     */
    public Snake(int newGridSize)
    {
        numCells = newGridSize;
        grid = new SnakeCell[numCells][numCells];

        initializeGrid();

        startX = numCells / 2;
        startY = numCells / 2;
        initializeSnake();

        addFruit();
        isLost = false;

    }


    // ----------------------------------------------------------
    // Methods
    /**
     * initializes the view
     */
    private void initializeGrid()
    {
        addWalls();
        addField();

    }


    // ----------------------------------------------------------
    /**
     * adds the field
     */
    private void addField()
    {
        for (int i = 1; i < getGridSize() - 1; i++)
        {
            for (int j = 1; j < getGridSize() - 1; j++)
            {
                setCell(i, j, SnakeCell.FIELD);
            }

        }
    }


    // ----------------------------------------------------------
    /**
     * this method is called when the game is reset
     */
    public void resetModel()
    {
        addField();
        initializeSnake();
        addFruit();
        setGameLost(false);

    }

    // ----------------------------------------------------------
    /**
     * initializes the snake
     */
    private void initializeSnake()
    {
        snakeSize = getGridSize() / 5;
        if (snake == null)
        {
            snake = new DoublyLinkedDeque<Location>();
        }
        else
        {
            snake.clear();
        }
// positive = false;
// vertical = true;
        snakeMotionDir = Direction.notMoving;

        for (int n = 0; n < snakeSize; n++)
        {
            Location thisLocation = new Location(startX, startY + n);

            snake.enqueueAtRear(thisLocation);
            setCell(startX, startY + n, SnakeCell.SNAKE);

        }
        score = 0;

    }

    // ----------------------------------------------------------
    /**
     * adds walls
     */
    private void addWalls()
    {
        for (int i = 0; i < getGridSize(); i++)
        {
            for (int j = 0; j < getGridSize(); j++)
            {
                if (i == 0 || i == (getGridSize() - 1) || j == 0
                    || j == (getGridSize() - 1))
                {
                    setCell(i, j, SnakeCell.WALL);
                }
            }

        }
    }

    // ----------------------------------------------------------
    /**
     * adds fruit
     */
    private void addFruit()
    {
        Random rnd = new Random();
        fruitX = rnd.nextInt(0, getGridSize() - 1);
        fruitY = rnd.nextInt(0, getGridSize() - 1);
// Random random = new sofia.util.Random();
// fruitX = random.nextInt(0, (size - 1));
// fruitY = random.nextInt(0, (size - 1));
        if (getCell(fruitX, fruitY) == SnakeCell.FIELD)
        {
            setCell(fruitX, fruitY, SnakeCell.FRUIT);
        }
        else
        {
            addFruit();
        }
    }


// public int getFruitX()
// {
// return fruitX;
// }
//
//
// public int getFruitY()
// {
// return fruitY;
// }

    // ----------------------------------------------------------
    /**
     * getter for the fruit location
     *
     * @return fruit location
     */
    public Location getFruitLocation()
    {
        Location fruitLocation = new Location(fruitX, fruitY);

        return fruitLocation;
    }

    // ----------------------------------------------------------
    /**
     * getter for the snake
     *
     * @return the doubly linked list snake
     */
    public DoublyLinkedDeque<Location> getSnake()
    {
        return snake;
    }

    // ----------------------------------------------------------
    /**
     * setter for grid cells
     *
     * @param x
     *            x-coordinate of cell
     * @param y
     *            y-coordinate of the cell
     * @param type
     *            enum type to which the cell must be set
     */
    public void setCell(int x, int y, SnakeCell type)
    {
        grid[y][x] = type;
    }

    // ----------------------------------------------------------
    /**
     * getter for the cell
     *
     * @param x
     *            x-coordinate of the cell
     * @param y
     *            y-coordinate of the cell
     * @return cell's enum type
     */
    public SnakeCell getCell(int x, int y)
    {
        return grid[y][x];
    }

    // ----------------------------------------------------------
    /**
     * moves the snake by one cell in the "snakeMotionDirection" direction if
     * the new cell is a fruit, snake grows if the new cell us a wall, game is
     * lost
     */
    private boolean move(int x, int y)
    {
        if (getCell(x, y) == SnakeCell.FIELD)
        {
            Location newLocation = new Location(x, y);

            snake.enqueueAtFront(newLocation);
            Location tempLocation = snake.dequeueAtRear();

            setCell(x, y, SnakeCell.SNAKE);
            setCell(tempLocation.x(), tempLocation.y(), SnakeCell.FIELD);
            return false;
        }
        else if (getCell(x, y) == SnakeCell.FRUIT)
        {
            Location newLocation = new Location(x, y);
            snake.enqueueAtFront(newLocation);

            setCell(x, y, SnakeCell.SNAKE);
            snakeSize++;
            score = score + 100;
            addFruit();
            System.out.println("Inside eating fruit");
            return true;
        }
        else
        {
            System.out.println("Game Lost: " + x + ", " + y + getCell(x, y));
            setGameLost(true);
            return false;
        }

    }

    // ----------------------------------------------------------
    /**
     * setter for game lost
     *
     * @param state
     */
    public void setGameLost(boolean state)
    {
        System.out.println("Setting Game Lost to" + state);
        isLost = state;
    }

    // ----------------------------------------------------------
    /**
     * wrapper method for moving the snake -- checks the direction to move in
     * and then calls move().
     *
     * @return whether the snake ate a fruit or not
     */
    public boolean moveInDirection()
    {
        Location front = snake.frontItem();

        boolean isFruitEaten = false;

        switch (snakeMotionDir)
        {
            case north:
                isFruitEaten = move(front.x(), front.y() - 1);
                break;

            case south:
                isFruitEaten = move(front.x(), front.y() + 1);
                break;

            case east:
                isFruitEaten = move(front.x() + 1, front.y());
                break;

            case west:
                isFruitEaten = move(front.x() - 1, front.y());
                break;

            default:
                break;
        }
        return isFruitEaten;

// if (positive && vertical)
// {
// move(front.x(), front.y() + 1);
// }
// else if (positive && !vertical)
// {
// move(front.x() + 1, front.y());
// }
// else if (!positive && vertical)
// {
// move(front.x(), front.y() - 1);
// }
// else
// {
// move(front.x() - 1, front.y());
// }

    }

    // ----------------------------------------------------------
    /**
     * setter for snake motion direction
     *
     * @param newDir
     *            the direction that we want to set for snake's move
     */
    public void setSnakeMotionDir(Direction newDir)
    {
        snakeMotionDir = newDir;
    }

    // ----------------------------------------------------------
    /**
     * getter for snake's motion direction
     *
     * @return direction in which the snake is currently set to move
     */
    public Direction getSnakeMotionDir()
    {
        return snakeMotionDir;
    }

    // ----------------------------------------------------------
    /**
     * getter for snake size
     *
     * @return size of the snake (DLL)
     */
    public int getSnakeSize()
    {
        return snakeSize;
    }

// public void setVertical(boolean isVertical)
// {
// vertical = isVertical;
// }
//
//
// public void setPositive(boolean isPositive)
// {
// positive = isPositive;
// }
//
//
// public boolean isVertical()
// {
// return vertical;
// }
//
//
// public boolean isPositive()
// {
// return positive;
// }

    // ----------------------------------------------------------
    /**
     * getter for whether the game is already lost or not (called by
     * processTouch)
     *
     * @return true or false
     */
    public boolean isLost()
    {
        return isLost;
    }

    // ----------------------------------------------------------
    /**
     * getter for grid size
     *
     * @return grid size
     */
    public int getGridSize()
    {
        return numCells;
    }

    // ----------------------------------------------------------
    /**
     * returns the current score
     *
     * @return current score
     */
    public int getScore()
    {
        return score;

    }

}
