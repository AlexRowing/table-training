package restaurant;

import student.TestCase;

/**
 * Test class of Table
 *
 * @author alex
 * @version Oct 14, 2026
 */
public class TableTest
    extends TestCase
{
    private Table table;

    /**
     * Creates a table with id 1 and 4 seats before each test
     */
    public void setUp()
    {
        table = new Table(1, 4);
    }


    /**
     * test Table() with valid parameters
     */
    public void testConstructor()
    {
        assertEquals(1, table.getTableID());
        assertEquals(4, table.getNumSeats());
        assertEquals(0, table.getNumPeople());
        assertEquals(0, table.getTimeSat());
        assertTrue(table.isEmpty());
        assertFalse(table.isReserved());
    }


    /**
     * test Table() with negative seats
     */
    public void testConstructorNegativeSeats()
    {
        Exception myIAE = null;
        try
        {
            new Table(1, -4);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test Table() with zero seats
     */
    public void testConstructorZeroSeats()
    {
        Exception myIAE = null;
        try
        {
            new Table(1, 0);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test getTableID()
     */
    public void testGetTableID()
    {
        assertEquals(1, table.getTableID());
    }


    /**
     * test getNumSeats()
     */
    public void testGetNumSeats()
    {
        assertEquals(4, table.getNumSeats());
    }


    /**
     * test setNumPeople() and getNumPeople()
     */
    public void testSetNumPeople()
    {
        table.setNumPeople(3);
        assertEquals(3, table.getNumPeople());
    }


    /**
     * test setTimeSat() and getTimeSat()
     */
    public void testSetTimeSat()
    {
        table.setTimeSat(30);
        assertEquals(30, table.getTimeSat());
    }


    /**
     * test setEmpty() and isEmpty()
     */
    public void testSetEmpty()
    {
        table.setEmpty(false);
        assertFalse(table.isEmpty());
        table.setEmpty(true);
        assertTrue(table.isEmpty());
    }


    /**
     * test setReserved() and isReserved()
     */
    public void testSetReserved()
    {
        table.setReserved(true);
        assertTrue(table.isReserved());
        table.setReserved(false);
        assertFalse(table.isReserved());
    }


    /**
     * test toString() when the table is empty
     */
    public void testToStringEmpty()
    {
        assertEquals("Table 1: 0/4 seats, Empty", table.toString());
    }


    /**
     * test toString() when the table is occupied
     */
    public void testToStringOccupied()
    {
        table.setNumPeople(2);
        table.setEmpty(false);
        assertEquals("Table 1: 2/4 seats, Occupied", table.toString());
    }


    /**
     * test toString() when the table is reserved
     */
    public void testToStringReserved()
    {
        table.setReserved(true);
        assertEquals("Table 1: 0/4 seats, Reserved", table.toString());
    }
}
