package restaurant;

/**
 * Table Class creates and manages table objects
 *
 * @author alex
 * @version Oct 14, 2026
 */
public class Table
{
    private int tableID;
    private int numSeats;
    private int numPeople;
    private int timeSat;
    private boolean empty;
    private boolean reserved;
    private Party[] partySat;
    public String res;

    /**
     * Create a new Table object. The table starts empty, not reserved, with
     * timeSat = 0
     *
     * @param tableID
     *            - id number of the table
     * @param numSeats
     *            - number of seats at the table
     * @throws IllegalArgumentException
     *             if numSeats is less than 1
     */
    public Table(int tableID, int numSeats)
    {
        if (numSeats < 1)
        {
            throw new IllegalArgumentException();
        }
        this.tableID = tableID;
        this.numSeats = numSeats;
        numPeople = 0;
        timeSat = 0;
        empty = true;
        reserved = false;
        partySat = new Party[1];
    }
    
    public Party getParty() {
        if (isEmpty()) {
            return null;
        }
        return partySat[0];
    }
    
    public void setParty(Party party) {
        partySat[0] = party;
    }

    /**
     * gets the name of the reservation holding the table
     * 
     * @return res name of reservation
     */
    public String getName() {
        return res;
    }
    /**
     * Gets the id of the table
     *
     * @return tableID
     */
    public int getTableID()
    {
        return tableID;
    }


    /**
     * Gets the capacity of the table
     *
     * @return numSeats
     */
    public int getNumSeats()
    {
        return numSeats;
    }


    /**
     * Gets the number of people sitting at the table
     *
     * @return numPeople
     */
    public int getNumPeople()
    {
        return numPeople;
    }


    /**
     * Sets the number of people sitting at the table
     *
     * @param numPeople
     *            - number of people sitting at the table
     */
    public void setNumPeople(int numPeople)
    {
        this.numPeople = numPeople;
    }


    /**
     * Gets how long the party has been seated at the table
     *
     * @return timeSat
     */
    public int getTimeSat()
    {
        return timeSat;
    }


    /**
     * Sets how long the party has been seated at the table
     *
     * @param timeSat
     *            - time the party has been seated
     */
    public void setTimeSat(int timeSat)
    {
        this.timeSat = timeSat;
    }


    /**
     * Checks if the table is empty
     *
     * @return true if the table is empty
     */
    public boolean isEmpty()
    {
        return empty;
    }


    /**
     * Checks if the table is reserved
     *
     * @return true if the table is reserved
     */
    public boolean isReserved()
    {
        return reserved;
    }


    /**
     * Updates whether the table is empty
     *
     * @param empty
     *            - true if the table is empty
     */
    public void setEmpty(boolean empty)
    {
        this.empty = empty;
    }


    /**
     * Updates whether the table is reserved
     *
     * @param name to be assigned to reserved table
     *
     * @param reserved
     *            - true if the table is reserved
     */
    public void setReserved(boolean reserved, String name)
    {
        res = name;
        this.reserved = reserved;
    }


    /**
     * Returns a string describing the table
     *
     * @return "Table 1: 2/4 seats, Occupied"
     */
    public String toString()
    {
        String status = "Empty";
        if (reserved)
        {
            status = "Reserved";
        }
        else if (!empty)
        {
            status = "Occupied";
        }
        return "Table " + tableID + ": " + numPeople + "/" + numSeats
            + " seats, " + status;
    }
}
