package com.groupproject.snake;

/**
 * This is the interface model for our app.
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version 2013.11.12
 */
public interface ISnake
{
    // Methods
    /**
     * This method sets the value of a cell to the requisite enum type.
     *
     * @param x
     *            the x coordinate of the cell
     * @param y
     *            the y coordinate of the cell
     * @param type
     *            the enum value to be assigned
     */
    void setCell(int x, int y, SnakeCell type);


    /**
     * This method returns the enum type value of the cell in question
     *
     * @param x
     *            x coordinate of the cell
     * @param y
     *            y coordinate of the cell
     * @return an enum type value
     */
    SnakeCell getCell(int x, int y);


    /**
     * This method determines whether or not the snake can move in the direction
     * determined by moveInDirection() method.
     *
     * @param x
     *            x coordinate of the cell into which the snake will move
     * @param y
     *            y coordinate of the cell into which the snake will move
     */
    void move(int x, int y);


    /**
     * This method determines the direction in which the snake can move.
     */
    void moveInDirection();


    /**
     * This method determines whether the snake's movement is bound to be
     * vertical or not
     *
     * @param isVertical
     *            true if the snake must move in the vertical direction, false
     *            otherwise.
     */
    void setVertical(boolean isVertical);


    /**
     * This method determines whether the snake's movement is bound to be in the
     * positive or the negative direction of x (or y) axis.
     *
     * @param isPositive
     *            true if the snake must move in positive direction, false
     *            otherwise.
     */
    void setPositive(boolean isPositive);

}
