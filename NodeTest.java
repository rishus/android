package com.groupproject.snake;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests for the {@link Node} class.
 *
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version 2013.03.10
 */
public class NodeTest
    extends TestCase
{
    // ~ Fields ................................................................

    private Node<String> node1;
    private Node<String> node2;
    private Node<String> node3;


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Create some new nodes for each test method.
     */
    public void setUp()
    {
        node1 = new Node<String>("node1");
        node2 = new Node<String>("node2");
        node3 = new Node<String>("node3");
    }


    /**
     * This tests that join works correctly
     */
    public void testJoin()
    {
        node1.setData("this is weird");
        node1.join(node2);
        assertEquals(node1.next(), node2);
        assertEquals(node2.previous(), node1);

        node3.join(null);
        assertEquals(node3.next(), null);

        Exception e = null;
        try
        {
            node3.join(node2);
        }
        catch (Exception thrown)
        {
            e = thrown;
        }
        assertTrue(e instanceof IllegalStateException);

        try
        {
            node1.join(node3);

        }
        catch (Exception thrown)
        {
            e = thrown;
        }
        assertTrue(e instanceof IllegalStateException);

    }


    /**
     * This tests that split works correctly
     */
    public void testSplit()
    {
        node1.join(node2.join(node3));
        node1.split();
        assertNull(node1.next());
        assertNull(node2.previous());

        assertNull(node3.split());
    }


    /**
     * Because webcat is angry
     */
    public void testSetData()
    {
        node1.setData("sup");
        assertEquals("sup", node1.data());
    }
}
