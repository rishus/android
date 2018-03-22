package com.groupproject.snake;

import java.util.EmptyStackException;

/**
 * A deque implemented using a doubly-linked chain with sentinel nodes at each
 * end.
 *
 * @param <T>
 *            The type of elements contained in the deque.
 * @author Pat Butler (patsfan1)
 * @author Brannon Mason (brannon1)
 * @author Rishu Saxena (rishus)
 * @version 2013.11.04
 */
public class DoublyLinkedDeque<T>
//    implements Deque<T>
{
    // ~ Instance/static variables .............................................

    // A reference to the sentinel node at the beginning of the deque.
    private Node<T> head;

    // A reference to the sentinel node at the end of the deque.
    private Node<T> tail;

    // The number of elements in the deque.
    private int     size;


    // ~ Constructor ...........................................................

    /**
     * Construct the stack.
     */
    public DoublyLinkedDeque()
    {
        head = new Node<T>(null);
        tail = new Node<T>(null);
        head.join(tail);
        size = 0;
    }


    // ~ Public methods ........................................................

    /**
     * Insert a new item at the rear (the tail) of the deque.
     *
     * @param value
     *            the item to insert.
     * @postcondition [new-contents] == [old-contents] * [value]
     */
    public void enqueueAtRear(T value)
    {
        Node<T> newNode = new Node<T>(value);

        Node<T> a = tail.previous();
        newNode.join(a.split());
        a.join(newNode);
        size++;

    }


    /**
     * Remove the item at the front (the head) of the deque.
     *
     * @return The item that was removed
     * @precondition |[old-contents]| > 0
     * @postcondition [old-contents] == [result] * [new-contents]
     */
    public T dequeueAtFront()
    {
        if (size() == 0)
        {
            throw new EmptyStackException();

        }
        else
        {
            Node<T> a = head.split();
            head.join(a.split());
            size--;
            return a.data();
        }
    }


    /**
     * Insert a new item at the front (the head) of the deque.
     *
     * @param value
     *            the item to insert.
     * @postcondition [new-contents] = [value] * [old-contents]
     */
    public void enqueueAtFront(T value)
    {
        Node<T> newNode = new Node<T>(value);

        newNode.join(head.split());
        head.join(newNode);
        size++;

    }


    /**
     * Remove the item at the rear (the tail) of the deque.
     *
     * @return The item that was removed
     * @precondition |[old-contents]| > 0
     * @postcondition [old-contents] = [new-contents] * [result]
     */
    public T dequeueAtRear()
    {
        if (size() == 0)
        {
            throw new EmptyStackException();

        }
        else
        {
            Node<T> a = tail.previous().previous();
            Node<T> b = a.split();
            b.split();
            a.join(tail);
            size--;
            return b.data();
        }

    }


    // ----------------------------------------------------------
    /**
     * Get the item at the front (the head) of the deque. Does not alter the
     * deque.
     *
     * @return the item at the front of the deque.
     * @precondition |[contents]| > 0
     * @postcondition [new-contents] == [old-contents] and [contents] = [result]
     *                * [rest-of-items]
     */
    public T frontItem()
    {
        if (size() == 0)
        {
            throw new EmptyStackException();
        }
        else
        {
            return head.next().data();
        }
    }


    // ----------------------------------------------------------
    /**
     * Get the item at the rear (the tail) of the deque. Does not alter the
     * deque.
     *
     * @return the item at the rear of the deque.
     * @precondition |[contents]| > 0
     * @postcondition [new-contents] == [old-contents] and [contents] =
     *                [rest-of-items] * [result]
     */
    public T rearItem()
    {
        if (size() == 0)
        {
            throw new EmptyStackException();
        }
        else
        {
            return tail.previous().data();
        }
    }


    // ----------------------------------------------------------
    /**
     * Get the number of items in this deque. Does not alter the deque.
     *
     * @return The number of items this deque contains.
     * @postcondition result = |[contents]|
     */
    public int size()
    {
        return size;
    }


    // ----------------------------------------------------------
    /**
     * Empty the deque.
     *
     * @postcondition [new-contents] = []
     */
    public void clear()
    {
        while (head.next() != tail)
        {
            head.join(head.split().split());
            size--;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns a string representation of this deque. A deque's string
     * representation is written as a comma-separated list of its contents (in
     * front-to-rear order) surrounded by square brackets, like this:
     *
     * <pre>
     * [52, 14, 12, 119, 73, 80, 35]
     * </pre>
     * <p>
     * An empty deque is simply <code>[]</code>.
     * </p>
     *
     * @return a string representation of the deque
     */
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        str.append("[");

        Node<T> ref = head.next();
        while (ref != tail)
        {
            str.append(ref.data().toString());
            if (ref.next() != tail)
            {
                str.append(", ");
            }
            ref = ref.next();

        }

        str.append("]");

        return str.toString();

    }
}
