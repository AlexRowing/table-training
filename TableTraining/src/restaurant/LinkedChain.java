package restaurant;

import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * Linked Chain Class
 * 
 * @param <T>
 *            - class is generic
 * @author sarah
 * @version Oct 13, 2026
 */
public class LinkedChain<T>
{
    // ~ Fields ................................................................
    Node<T> head;
    int size;

    // ~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new LinkedChain object. Initializes empty chain with head = null
     * and size = 0
     */
    public LinkedChain()
    {
        head = null;
        size = 0;
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Adds an item to the back of the chain
     * 
     * @param item
     *            - data to go into new Node in chain
     */
    public void add(T item)
    {
        Node<T> newItem = new Node<T>(item);
        if (size == 0)
        {
            head = newItem;
        }
        else
        {
            Node<T> curr = head;
            while (curr.next() != null)
            {
                curr = curr.next();
            }
            curr.setNext(newItem);
        }
        size++;
    }


    // ----------------------------------------------------------
    /**
     * Removes a specific item in the chain
     * 
     * @param item
     *            - Node to be removed
     * @return Node Removed throw NoSuchElementException if the item is not in
     *             the chain
     */
    public T remove(T item)
    {
        if (size == 0)
        {
            throw new NoSuchElementException();
        }
        if (this.contains(item))
        {
            Node<T> curr = head;
            if (curr.data().equals(item))
            {
                head = head.next();
                size--;
                return item;
            }
            while (curr.next() != null && curr.next().data().equals(item))
            {
                curr = curr.next();
            }
            curr.setNext(curr.next().next());
            size--;
            return item;
        }
        throw new NoSuchElementException();
    }


    // ----------------------------------------------------------
    /**
     * Checks if an item occurs in the chain
     * 
     * @param item
     *            - Node being searched for
     * @return true if item is in chain
     */
    public boolean contains(T item)
    {
        if (size == 0)
        {
            return false;
        }
        Node<T> curr = head;
        while (curr != null)
        {
            if (curr.data().equals(item))
            {
                return true;
            }
            curr = curr.next();
        }
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Get the head Node of the chain
     * 
     * @return Node referenced by head (first item in chain)
     */
    public Node<T> getHead()
    {
        return head;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @return the size of the chain
     */
    public int getSize()
    {
        return size;
    }


    // ----------------------------------------------------------
    /**
     * Counts the amount of times an item occurs in the chain
     * 
     * @param item
     *            - data to be searched for
     * @return count - number of occurrences of item in chain
     */
    public int getFrequency(T item)
    {
        if (size == 0)
        {
            return 0;
        }
        int count = 0;
        Node<T> curr = head;
        while (curr != null)
        {
            if (curr.data().equals(item))
            {
                count++;
            }
            curr = curr.next();
        }
        return count;
    }


    public String toString()
    {
        String chain = "";
        Node<T> curr = head;
        for (int i = 0; i < size; i++)
        {
            chain += curr.data().toString();
            if (curr.next() != null)
            {
                chain += " --> ";
            }
            curr = curr.next();
        }
        return chain;
    }
}
