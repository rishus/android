package com.groupproject.snake;

/**
 * The Location class represents a simple/single location that has x and y
 * coordinates, represented as an (x, y) pair. * @author Pat Butler (patsfan1)
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version 2013.10.04
 */
public class Location
{
    // data fields
    /**
     * the x coordinate
     */
    int x;
    /**
     * the y coordinate
     */
    int y;


    /**
     * Creates a new Location object.
     *
     * @param newX
     *            the x coordinate of the location, must be an integer
     * @param newY
     *            the y coordinate of the location, must be an integer
     */
    // constructor
    public Location(int newX, int newY)
    {
        x = newX;
        y = newY;

    }


    // methods

    /**
     * getter for the x coordinate of the current location
     *
     * @return the x coordinate
     */
    public int x()
    {
        return x;
    }


    /**
     * getter for the y coordinate of the current location
     *
     * @return the y coordinate
     */
    public int y()
    {
        return y;
    }


    /**
     * compares two locations
     *
     * @param loc
     *            object of any type
     * @return boolean true or false
     */
    public boolean equals(Object loc)
    {
        if (loc instanceof Location)
        {
            return (x == ((Location)loc).x()) && (y == ((Location)loc).y());
        }
        else
        {
            return false;
        }

    }


    /**
     * Returns a summary description of the location
     *
     * @return a string representation of the location
     */
    public String toString()
    {
        String str = "(" + x + ", " + y + ")";

        return str;
    }

}
