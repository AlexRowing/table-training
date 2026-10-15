package restaurant;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test class for Seating Class
 * 
 * @author sarah
 * @version Oct 15, 2026
 */
public class SeatingTest
    extends TestCase
{
    // ~ Fields ................................................................
    Seating waitlist;

    // ~ Set up ..........................................................
    public void setUp()
    {
        waitlist = new Seating();
    }


    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Test addParty()
     */
    public void testAddParty()
    {
        waitlist.addParty("Sarah", 3);
        assertEquals("Sarah (party of 3)", waitlist.getWaitList().toString());
        waitlist.addParty("Erin", 6);
        assertEquals(
            "Sarah (party of 3) --> Erin (party of 6)",
            waitlist.getWaitList().toString());

    }


    // ----------------------------------------------------------
    /**
     * Test removeParty()
     */
    public void testRemoveParty()
    {
        waitlist.addParty("Sarah", 3);
        waitlist.addParty("Erin", 6);
        waitlist.removeParty("Sarah", 3);
        assertEquals("Erin (party of 6)", waitlist.getWaitList().toString());
        waitlist.removeParty("Erin", 6);
        assertEquals("", waitlist.getWaitList().toString());
    }


    // ----------------------------------------------------------
    /**
     * Test addReservation()
     */
    public void testAddReservation()
    {
        waitlist.addReservation("Sarah", 3, 13, 20);
        assertEquals(
            "Sarah (party of 3) - 1:20 PM",
            waitlist.getWaitList().toString());
        waitlist.addReservation("Erin", 6, 15, 50);
        assertEquals(
            "Sarah (party of 3) - 1:20 PM --> Erin (party of 6) - 3:50 PM",
            waitlist.getWaitList().toString());

    }


    // ----------------------------------------------------------
    /**
     * Test removeReservation
     */
    public void testRemoveReservation()
    {
        waitlist.addReservation("Sarah", 3, 13, 20);
        waitlist.addReservation("Erin", 6, 15, 50);
        waitlist.removeReservation("Sarah", 3, 13, 20);
        assertEquals(
            "Erin (party of 6) - 3:50 PM",
            waitlist.getWaitList().toString());
        waitlist.removeReservation("Erin", 6, 15, 50);
        assertEquals("", waitlist.getWaitList().toString());

    }


    // ----------------------------------------------------------
    /**
     * Test getWaitList()
     */
    public void testGetWaitList()
    {
        waitlist.addReservation("Sarah", 3, 13, 20);
        assertEquals(
            "Sarah (party of 3) - 1:20 PM",
            waitlist.getWaitList().toString());
        waitlist.addReservation("Erin", 6, 15, 50);
        assertEquals(
            "Sarah (party of 3) - 1:20 PM --> Erin (party of 6) - 3:50 PM",
            waitlist.getWaitList().toString());
        waitlist.addParty("Adam", 4);
        assertEquals(
            "Sarah (party of 3) - 1:20 PM --> Erin (party of 6) - 3:50 PM --> Adam (party of 4)",
            waitlist.getWaitList().toString());
    }


    // ----------------------------------------------------------
    /**
     * Test contains()
     */
    public void testContains()
    {
        waitlist.addReservation("Sarah", 3, 13, 20);
        waitlist.addReservation("Erin", 6, 15, 50);
        waitlist.addParty("Adam", 4);
        assertTrue(waitlist.contains("Erin", 6, 15, 50));
        assertFalse(waitlist.contains("Sarah", 6, 15, 50));
        assertTrue(waitlist.contains("Adam", 4));
        assertFalse(waitlist.contains("Sarah", 3));
    }


    // ----------------------------------------------------------
    /**
     * Test compareParties()
     */
    public void testCompareParties()
    {
        waitlist.addReservation("Sarah", 3, 13, 20);
        waitlist.addReservation("Erin", 6, 15, 50);
        waitlist.addParty("Adam", 4);
        Reservation test1 = new Reservation("Erin", 6, 15, 50);
        Reservation test2 = new Reservation("Sarah", 6, 13, 20);
        Party test3 = new Party("Adam", 4);

        assertEquals(test1, waitlist.compareParties(6));
        assertEquals(test2, waitlist.compareParties(3));
        assertEquals(test3, waitlist.compareParties(5));
    }


    // ----------------------------------------------------------
    /**
     * Test seatTables()
     */
    public void testSeatTables()
    {
        waitlist.addReservation("Sarah", 3, 13, 20);
        waitlist.addReservation("Erin", 6, 15, 50);
        waitlist.addParty("Adam", 4);
        Display tables = new Display();
        tables.addTable(new Table(1, 3));
        tables.addTable(new Table(2, 5));
        tables.addTable(new Table(3, 6));
        waitlist.seatTables(tables.getTables());
        assertFalse(tables.getTables().get(0).isEmpty());
        assertFalse(tables.getTables().get(1).isEmpty());
        assertFalse(tables.getTables().get(2).isEmpty());

    }
}
