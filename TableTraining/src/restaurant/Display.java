package restaurant;

import java.util.ArrayList;

/**
 * Display Class shows restaurant data in the console and stores the table
 * ArrayList
 *
 * @author alex
 * @version Oct 14, 2026
 */
public class Display
{
    private ArrayList<Table> tableArray;
    private ArrayList<Table> combinedTableArray;

    /**
     * Create a new Display object. Initializes an empty table list and no
     * combined tables
     */
    public Display()
    {
        tableArray = new ArrayList<Table>();
        combinedTableArray = null;
    }


    /**
     * Adds a table to the restaurant
     *
     * @param table
     *            - table to be added
     * @throws IllegalArgumentException
     *             if a table with the same id already exists
     */
    public void addTable(Table table)
    {
        if (contains(table.getTableID()))
        {
            throw new IllegalArgumentException(
                "Table " + table.getTableID() + " is already initiated");
        }
        tableArray.add(table);
    }


    /**
     * Gets the list of tables currently in use. If tables are combined this is
     * the combined list, otherwise it is the original list
     *
     * @return the current table list
     */
    public ArrayList<Table> getTables()
    {
        if (combinedTableArray != null)
        {
            return combinedTableArray;
        }
        return tableArray;
    }


    /**
     * Gets a table by its id
     *
     * @param tableID
     *            - id of the table
     * @return the table with that id
     * @throws IllegalArgumentException
     *             if the table does not exist
     */
    public Table getTable(int tableID)
    {
        for (Table table : getTables())
        {
            if (table.getTableID() == tableID)
            {
                return table;
            }
        }
        throw new IllegalArgumentException("Please enter a valid table ID");
    }


    /**
     * Fills a table with the number of people sitting at it
     *
     * @param tableID
     *            - id of the table
     * @param numPeople
     *            - number of people sitting at the table
     * @throws IllegalArgumentException
     *             if numPeople is negative or more than the table's seats
     */
    public void setTableSeats(int tableID, int numPeople)
    {
        Table table = getTable(tableID);
        if (numPeople < 0 || numPeople > table.getNumSeats())
        {
            throw new IllegalArgumentException("Invalid number of seats");
        }
        table.setNumPeople(numPeople);
        table.setEmpty(numPeople == 0);
    }


    /**
     * Returns a string of every table and its details, one table per line
     *
     * @return all tables as a string
     */
    public String toString()
    {
        String tables = "";
        for (Table table : getTables())
        {
            tables += table.toString() + "\n";
        }
        return tables;
    }


    /**
     * Prints a visual display of the tables to the console
     */
    public void showTables()
    {
        for (Table table : getTables())
        {
            String seats = "";
            for (int i = 0; i < table.getNumSeats(); i++)
            {
                if (i < table.getNumPeople())
                {
                    seats += "X";
                }
                else
                {
                    seats += "O";
                }
            }
            System.out.println("[" + table.getTableID() + "] " + seats);
        }
    }


    /**
     * Combines two tables into one temporary table. The temporary table takes
     * the id of the first table and the sum of both tables' seats
     *
     * @param tableID1
     *            - id of the first table
     * @param tableID2
     *            - id of the second table
     * @return the temporary combined table
     * @throws IllegalArgumentException
     *             if either table does not exist or the ids are the same
     * @throws IllegalStateException
     *             if tables are already combined
     */
    public Table combineTables(int tableID1, int tableID2)
    {
        if (combinedTableArray != null)
        {
            throw new IllegalStateException("Tables are already combined");
        }
        if (tableID1 == tableID2)
        {
            throw new IllegalArgumentException(
                "Please enter two different tables");
        }
        Table first = getTable(tableID1);
        Table second = getTable(tableID2);
        Table combined =
            new Table(tableID1, first.getNumSeats() + second.getNumSeats());

        combinedTableArray = new ArrayList<Table>();
        for (Table table : tableArray)
        {
            if (table != first && table != second)
            {
                combinedTableArray.add(table);
            }
        }
        combinedTableArray.add(combined);
        return combined;
    }


    /**
     * Separates the temporary combined table back into the original tables
     *
     * @param tableID
     *            - id of the combined table
     * @throws IllegalArgumentException
     *             if the table is not combined
     */
    public void separateTables(int tableID)
    {
        if (combinedTableArray == null || combinedTableArray
            .get(combinedTableArray.size() - 1).getTableID() != tableID)
        {
            throw new IllegalArgumentException("Table is not combined");
        }
        combinedTableArray = null;
    }


    /**
     * Finds the first table that is empty and not reserved
     *
     * @return an available table, null if no tables are available
     */
    public Table tableAvailability()
    {
        for (Table table : getTables())
        {
            if (table.isEmpty() && !table.isReserved())
            {
                return table;
            }
        }
        return null;
    }


    /**
     * Gets how long a party has been seated at a table
     *
     * @param tableID
     *            - id of the table
     * @return time the party has been seated
     */
    public int getTableTime(int tableID)
    {
        return getTable(tableID).getTimeSat();
    }


    /**
     * Sets how long a party has been seated at a table
     *
     * @param tableID
     *            - id of the table
     * @param time
     *            - time the party has been seated
     */
    public void setTableTime(int tableID, int time)
    {
        getTable(tableID).setTimeSat(time);
    }


    /**
     * Sets the time of a table to zero after it has been emptied
     *
     * @param tableID
     *            - id of the table
     */
    public void resetTableTime(int tableID)
    {
        getTable(tableID).setTimeSat(0);
    }


    /**
     * Increases the time of every occupied table by 10 minutes
     */
    public void updateTableTimes()
    {
        for (Table table : getTables())
        {
            if (!table.isEmpty())
            {
                table.setTimeSat(table.getTimeSat() + 10);
            }
        }
    }


    /**
     * Checks if a table is in the restaurant
     *
     * @param tableID
     *            - id of the table
     * @return true if the table exists
     */
    public boolean contains(int tableID)
    {
        for (Table table : getTables())
        {
            if (table.getTableID() == tableID)
            {
                return true;
            }
        }
        return false;
    }


    /**
     * adds all of the total seats