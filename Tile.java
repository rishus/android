package com.groupproject.snake;

import sofia.graphics.Color;
import sofia.graphics.RectangleShape;

/**
 * This creates the tiles that cover the board
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version Nov 15, 2013
 */
public class Tile
    extends RectangleShape
{
    // fields

    // constructor
    /**
     * Create a new CoverTile object.
     *
     * @param left
     *            left corner of the tile
     * @param top
     *            top corner of the tile
     * @param right
     *            right corner of the tile
     * @param bottom
     *            bottom corner of the tile
     * @param type
     *            an enum type determining whether the cell is a wall or a field
     */
    public Tile(float left, float top, float right, float bottom, SnakeCell type)
    {
        super(left, top, right, bottom);
        if (type.equals(SnakeCell.WALL))
        {
            setFillColor(Color.red);
        }
        else
        {
            setFillColor(Color.green);
        }

    }

}
