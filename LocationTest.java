package com.groupproject.snake;

/**
 * This class is meant to test class Location.
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version 2013.10.04
 */
public class LocationTest
    extends student.TestCase
{
    // data fields
    private Location testLocation1;
    private Location testLocation2;


    // *************************************
    // *** constructor ***
    // *************************************
    /**
     * Creates a new LocationTest object.
     */
    public LocationTest()
    {

        // leave constructor for unit test empty

    }


    // *************************************
    // *** set up ***
    // *************************************
    /**
     * Creates a brand new locations as the test fixture for each method
     */
    // setup
    public void setUp()
    {
        testLocation1 = new Location(0, 0);
        testLocation2 = new Location(3, 4);

    }


    // *************************************
    // *** test methods ***
    // *************************************
    /**
     * Testing whether the "location is assigned correctly or not naturally
     * checks the toString method as well
     */
    public void testToString()
    {
        assertEquals("(3, 4)", testLocation2.toString());
    }


    /**
     * testing the "return" methods
     */
    public void testReturns()
    {
        assertEquals(3, testLocation2.x());
        assertEquals(4, testLocation2.y());
    }


    /**
     * tests equals method
     */
    public void testEquals()
    {
        assertTrue(testLocation1.equals(testLocation1));

        assertFalse(testLocation2.equals(testLocation1));

        assertFalse(testLocation1.equals("someString"));

    }

}
