package restaurant;

import student.TestCase;

/**
 * Test class of Display
 *
 * @author alex
 * @version Oct 14, 2026
 */
public class DisplayTest
    extends TestCase
{
    private Display display;

    /**
     * Creates a display with table 1 (4 seats) and table 2 (2 seats) before
     * each test
     */
    public void setUp()
    {
        display = new Display();
        display.addTable(new Table(1, 4));
        display.addTable(new Table(2, 2));
    }


    /**
     * test addTable() with a new table
     */
    public void testAddTable()
    {
        display.addTable(new Table(3, 6));
        assertTrue(display.contains(3));
        assertEquals(3, display.getTables().size());
    }


    /**
     * test addTable() when the table id already exists
     */
    public void testAddTableDuplicate()
    {
        Exception myIAE = null;
        try
        {
            display.addTable(new Table(1, 5));
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
        assertEquals(2, display.getTables().size());
    }


    /**
     * test getTable() when the table exists
     */
    public void testGetTable()
    {
        assertEquals(1, display.getTable(1).getTableID());
    }


    /**
     * test getTable() when the table does not exist
     */
    public void testGetTableAbsent()
    {
        Exception myIAE = null;
        try
        {
            display.getTable(5);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test setTableSeats() with a valid number of people
     */
    public void testSetTableSeats()
    {
        display.setTableSeats(1, 4);
        assertEquals(4, display.getTable(1).getNumPeople());
        assertFalse(display.getTable(1).isEmpty());
    }


    /**
     * test setTableSeats() with zero people empties the table
     */
    public void testSetTableSeatsZero()
    {
        display.setTableSeats(1, 3);
        display.setTableSeats(1, 0);
        assertEquals(0, display.getTable(1).getNumPeople());
        assertTrue(display.getTable(1).isEmpty());
    }


    /**
     * test setTableSeats() with a negative number of people
     */
    public void testSetTableSeatsNegative()
    {
        Exception myIAE = null;
        try
        {
            display.setTableSeats(1, -1);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test setTableSeats() with more people than seats
     */
    public void testSetTableSeatsTooMany()
    {
        Exception myIAE = null;
        try
        {
            display.setTableSeats(2, 3);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test toString()
     */
    public void testToString()
    {
        display.setTableSeats(2, 2);
        assertEquals(
            "Table 1: 0/4 seats, Empty\nTable 2: 2/2 seats, Occupied\n",
            display.toString());
    }


    /**
     * test showTables()
     */
    public void testShowTables()
    {
        display.setTableSeats(1, 2);
        display.showTables();
        String output = systemOut().getHistory();
        assertTrue(output.contains("[1] XXOO"));
        assertTrue(output.contains("[2] OO"));
    }


    /**
     * test combineTables() with two valid tables
     */
    public void testCombineTables()
    {
        Table combined = display.combineTables(1, 2);
        assertEquals(1, combined.getTableID());
        assertEquals(6, combined.getNumSeats());
        assertEquals(1, display.getTables().size());
        assertFalse(display.contains(2));
    }


    /**
     * test combineTables() when a table does not exist
     */
    public void testCombineTablesAbsent()
    {
        Exception myIAE = null;
        try
        {
            display.combineTables(1, 10);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test combineTables() with the same table twice
     */
    public void testCombineTablesSame()
    {
        Exception myIAE = null;
        try
        {
            display.combineTables(1, 1);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test combineTables() when tables are already combined
     */
    public void testCombineTablesAlreadyCombined()
    {
        display.addTable(new Table(3, 2));
        display.combineTables(1, 2);
        Exception myISE = null;
        try
        {
            display.combineTables(1, 3);
        }
        catch (IllegalStateException ise)
        {
            myISE = ise;
        }
        assertNotNull(myISE);
    }


    /**
     * test separateTables() after tables are combined
     */
    public void testSeparateTables()
    {
        display.combineTables(1, 2);
        display.separateTables(1);
        assertEquals(2, display.getTables().size());
        assertEquals(4, display.getTable(1).getNumSeats());
        assertTrue(display.contains(2));
    }


    /**
     * test separateTables() when the table is not combined
     */
    public void testSeparateTablesNotCombined()
    {
        Exception myIAE = null;
        try
        {
            display.separateTables(1);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test separateTables() with the wrong table id
     */
    public void testSeparateTablesWrongID()
    {
        display.addTable(new Table(3, 2));
        display.combineTables(1, 2);
        Exception myIAE = null;
        try
        {
            display.separateTables(3);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test tableAvailability() when a table is available
     */
    public void testTableAvailability()
    {
        display.setTableSeats(1, 2);
        assertEquals(2, display.tableAvailability().getTableID());
    }


    /**
     * test tableAvailability() skips reserved tables
     */
    public void testTableAvailabilityReserved()
    {
        display.getTable(1).setReserved(true);
        assertEquals(2, display.tableAvailability().getTableID());
    }


    /**
     * test tableAvailability() when no tables are available
     */
    public void testTableAvailabilityNone()
    {
        display.setTableSeats(1, 4);
        display.getTable(2).setReserved(true);
        assertNull(display.tableAvailability());
    }


    /**
     * test setTableTime() and getTableTime()
     */
    public void testSetTableTime()
    {
        display.setTableTime(1, 20);
        assertEquals(20, display.getTableTime(1));
    }


    /**
     * test getTableTime() when the table does not exist
     */
    public void testGetTableTimeAbsent()
    {
        Exception myIAE = null;
        try
        {
            display.getTableTime(5);
        }
        catch (IllegalArgumentException iae)
        {
            myIAE = iae;
        }
        assertNotNull(myIAE);
    }


    /**
     * test resetTableTime()
     */
    public void testResetTableTime()
    {
        display.setTableTime(1, 40);
        display.resetTableTime(1);
        assertEquals(0, display.getTableTime(1));
    }


    /**
     * test updateTableTimes() only updates occupied tables
     */
    public void testUpdateTableTimes()
    {
        display.setTableSeats(1, 2);
        display.updateTableTimes();
        display.updateTableTimes();
        assertEquals(20, display.getTableTime(1));
        assertEquals(0, display.getTableTime(2));
    }


    /**
     * test contains() when the table exists
     */
    public void testContainsTrue()
    {
        assertTrue(display.contains(1));
    }


    /**
     * test contains() when the table does not exist
     */
    public void testContainsFalse()
    {
        assertFalse(display.contains(5));
    }
}
