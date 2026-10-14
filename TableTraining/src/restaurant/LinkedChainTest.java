package restaurant;

import java.util.NoSuchElementException;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test class of LinkedChain
 * 
 * @author sarah
 * @version Oct 13, 2026
 */
public class LinkedChainTest
    extends TestCase
{
    // ~ Fields ................................................................
    LinkedChain<String> myChain;

    // ~ Set Up ..........................................................
    public void setUp()
    {
        myChain = new LinkedChain<String>();
    }
    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * test Add() when chain is empty
     */
    public void testAddEmpty()
    {
        myChain.add("Hello");
        assertEquals("Hello", myChain.getHead().data());
        assertEquals(1, myChain.getSize());
    }


    // ----------------------------------------------------------
    // ----------------------------------------------------------
    /**
     * test Add() when chain is not empty
     */
    public void testAddNotEmpty()
    {
        myChain.add("Hello");
        myChain.add("Goodbye");
        assertEquals("Hello", myChain.getHead().data());
        assertNotNull(myChain.getHead().next());
        assertEquals(2, myChain.getSize());
    }


    // ----------------------------------------------------------
    /**
     * test remove() when chain is empty
     */
    public void testRemoveEmpty()
    {
        Exception myNSE = null;
        try
        {
            myChain.remove("Hello");
        }
        catch (NoSuchElementException nse)
        {
            myNSE = nse;
        }
        assertNotNull(myNSE);

    }


    // ----------------------------------------------------------
    /**
     * test remove() when the item is in the chain
     */
    public void testRemoveNotEmptyItemPresent()
    {
        myChain.add("Hello");
        myChain.add("Goodbye");
        myChain.add("See you later");
        assertEquals("Goodbye", myChain.remove("Goodbye"));
        assertEquals(2, myChain.getSize());
    }


    // ----------------------------------------------------------
    /**
     * test remove() when the item is not in the chain
     */
    public void testRemoveNotEmptyItemAbsent()
    {
        myChain.add("Hello");
        Exception myNSE = null;
        try
        {
            myChain.remove("Goodbye");
        }
        catch (NoSuchElementException nse)
        {
            myNSE = nse;
        }
        assertNotNull(myNSE);
    }


    // ----------------------------------------------------------
    /**
     * test contains() when item is present in chain
     */
    public void testContainsTrue()
    {
        myChain.add("Hello");
        assertTrue(myChain.contains("Hello"));

    }


    // ----------------------------------------------------------
    /**
     * test contains() when item is not present in chain
     */
    public void testContainsFalse()
    {
        myChain.add("Goodbye");
        assertFalse(myChain.contains("Hello"));
    }


    // ----------------------------------------------------------
    /**
     * test contains() when chain is empty
     */
    public void testContainsEmpty()
    {
        assertFalse(myChain.contains("Hello"));

    }


    // ----------------------------------------------------------
    /**
     * Test getSize() when chain is empty
     */
    public void testGetSizeEmpty()
    {
        assertEquals(0, myChain.getSize());

    }


    // ----------------------------------------------------------
    /**
     * Test getSize() when chain is not empty
     */
    public void testGetSizeNotEmpty()
    {
        myChain.add("Hello");
        assertEquals(1, myChain.getSize());

    }


    // ----------------------------------------------------------
    /**
     * Test getHead() when chain is empty
     */
    public void testGetHeadEmpty()
    {
        assertNull(myChain.getHead());
    }


    // ----------------------------------------------------------
    /**
     * Test getHead() when chain is not empty
     */
    public void testGetHeadNotEmpty()
    {
        myChain.add("Hello");
        assertNotNull(myChain.getHead());
    }


    // ----------------------------------------------------------
    /**
     * test getFrequency() when chain is empty
     */
    public void testGetFrequencyEmpty()
    {
        assertEquals(0, myChain.getFrequency("Hello"));
    }


    // ----------------------------------------------------------
    /**
     * test getFrequency() when chain is not empty
     */
    public void testGetFrequencyNotEmpty()
    {
        myChain.add("Hello");
        myChain.add("Goodbye");
        myChain.add("Hello");
        myChain.add("Hello");
        myChain.add("Hola");
        myChain.add("Goodbye");
        myChain.add("Hello");

        assertEquals(4, myChain.getFrequency("Hello"));
        assertEquals(2, myChain.getFrequency("Goodbye"));
        assertEquals(1, myChain.getFrequency("Hola"));
        assertEquals(0, myChain.getFrequency("Adios"));

    }


    // ----------------------------------------------------------
    /**
     * test toString()
     */
    public void testToString()
    {
        myChain.add("Hello");
        myChain.add("Goodbye");
        myChain.add("Hola");
        myChain.add("Hello");

        assertEquals(
            "Hello --> Goodbye --> Hola --> Hello",
            myChain.toString());

    }
}
