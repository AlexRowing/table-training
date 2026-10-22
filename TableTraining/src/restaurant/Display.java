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
     * Prints a text display of the tables to the console
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


    // ----------------------------------------------------------
    /**
     * Prints the tables in a visual format to the console STRETCH GOAL!
     */
    public void showTablesVisually()
    {
        ArrayList<String> restaurant = new ArrayList<String>();
        ArrayList<Integer> tableIDs = new ArrayList<Integer>();
        String L1 = "|";
        String buffer = "    ";
        String W1 = "____";
        int wScale;
        int lScale;

        String chairBuffer1 = " ";
        String chairBuffer2 = "    ";
        String chairBottom = "{_}";
        String chairBack1 = "_";
        String chairBack2 = "[_";
        String chairBack3 = "_]";
        String chairArm1 = "{ }";
        String chairArm2 = "_";

        int numChairTop = 0;
        int numChairLeft = 0;
        int numChairRight = 0;
        int numChairBottom = 0;
        int seats = 0;

        int currentArraySize = tableArray.size();
        ArrayList<Table> currentArray = tableArray;
        if (combinedTableArray != null)
        {
            currentArraySize = combinedTableArray.size();
            currentArray = combinedTableArray;
        }

        for (int t = 0; t < currentArraySize; t++)
        {
            Table currentTableObject = currentArray.get(t);
            if (currentTableObject == null)
            {
                continue;
            }
            tableIDs.add(currentTableObject.getTableID());
            seats = currentTableObject.getNumSeats();
            boolean middleMarked = false;
            boolean timeStampNext = false;
            String middleMark = "    ";
            String timeStamp = "   ";
            if (currentTableObject.isReserved())
            {
                middleMark = "R    ";
            }
            else
            {
                if (currentTableObject.isEmpty())
                {
                    middleMark = "0/" + currentTableObject.getNumSeats();
                }
                else
                {
                    middleMark = currentTableObject.getNumPeople() + "/"
                        + currentTableObject.getNumSeats();
                    timeStamp = ":" + currentTableObject.getTimeSat();
                }
                if (currentTableObject.getNumSeats() % 10 > 4)
                {
                    middleMark += " ";
                }
                if (currentTableObject.getNumPeople() < 10)
                {
                    middleMark += " ";
                }
            }
            // Table table = tableArray.get(0);
            // int seats = table.getNumSeats();

            // assign seats and table sizes for current table
            if (seats == 2)
            {
                lScale = 2;
                wScale = 2;
                numChairTop = 0;
                numChairBottom = 0;
                numChairLeft = 1;
                numChairRight = 1;
            }
            else if (seats <= 6)
            {
                lScale = 3;
                wScale = 2;
                numChairTop = 1;
                numChairBottom = 1;
                numChairRight = 1;
                numChairLeft = 1;
                if (seats == 3)
                {
                    numChairRight = 0;
                }
                else if (seats >= 5)
                {
                    wScale = 4;
                    numChairTop = 2;
                    numChairBottom = 2;
                    if (seats == 5)
                    {
                        numChairBottom = 1;
                    }
                }
            }
            else if (seats <= 10)
            {
                lScale = 5;
                wScale = 4;
                numChairTop = 2;
                numChairBottom = 2;
                numChairLeft = 2;
                numChairRight = 2;
                if (seats == 7)
                {
                    numChairBottom = 1;
                }
                if (seats >= 9)
                {
                    wScale = 6;
                    numChairBottom = 3;
                    numChairTop = 3;
                    if (seats == 9)
                    {
                        numChairTop = 2;
                    }
                }
            }
            else
            {
                lScale = 8;
                wScale = 6;
                numChairLeft = 3;
                numChairRight = 3;
                numChairTop = 3;
                numChairBottom = 2;
                if (seats == 12)
                {
                    numChairBottom = 3;
                }

            }

            // Draw table with seats!
            String currentTable = "";
            // Top chairs

            for (int i = 0; i < numChairTop; i++)
                currentTable +=
                    chairBuffer1 + "  " + chairBuffer2 + chairBack1 + " ";
            currentTable += "\n";
            for (int i = 0; i < numChairTop; i++)
                currentTable += "  " + chairBuffer2 + chairArm1;

            // Top edge
            currentTable += "\n    ";

            for (int i = 0; i < wScale; i++)
            {
                currentTable += W1;
            }
            currentTable += "\n";

            // Sides
            for (int i = 0; i < lScale; i++)
            {
                // Left chairs
                if (i == 0 || i == 3 || i == 6)
                {
                    currentTable += " " + chairArm2 + " " + L1;
                }
                else if (i == 1 || i == 4 || i == 7)
                {
                    currentTable += chairBack2 + " " + L1;
                }
                else
                {
                    currentTable += "   " + L1;
                }
                for (int j = 0; j < wScale; j++)
                {
                    currentTable += buffer;
                    if (timeStampNext)
                    {
                        currentTable += timeStamp + " ";
                        if (seats >= 11)
                        {
                            currentTable += buffer + buffer + buffer + "    ";
                        }
                        else if (wScale >= 6)
                        {
                            currentTable += buffer + buffer + buffer + "    ";
                        }
                        else if (wScale >= 4)
                        {
                            currentTable += buffer + buffer;
                        }
                    }

                    if (j == 0)
                    {
                        if (i == (lScale) / 2)
                        {
                            currentTable += middleMark;
                            middleMarked = true;
                            if (seats >= 11)
                            {
                                currentTable += buffer + "           ";
                            }
                            else if (wScale >= 6)
                            {
                                currentTable += buffer + "           ";
                            }
                            else if (wScale >= 4)
                            {
                                currentTable += "       ";

                            }

                        }
                    }
                    if (timeStampNext)
                    {
                        timeStampNext = false;
                        break;
                    }
                    if (middleMarked)
                    {
                        middleMarked = false;
                        timeStampNext = true;
                        break;

                    }
                }

                // Right chairs
                currentTable += L1 + " ";
                if ((i == 0 || i == 3 || i == 6) && numChairRight != 0)
                {
                    currentTable += chairArm2 + "\n";
                }
                else if ((i == 1 || i == 4 || i == 7) && numChairRight != 0)
                {
                    currentTable += chairBack3 + "\n";
                }
                else
                {
                    currentTable += "\n";
                }
            }

            // Bottom edge
            currentTable += "   |";
            for (int i = 0; i < wScale; i++)
            {
                currentTable += W1;
            }
            currentTable += L1 + "\n\n";

            // Bottom chairs
            for (int i = 0; i < numChairBottom; i++)
                currentTable += "  " + chairBuffer2 + chairBottom;

            restaurant.add(currentTable);
        }

        // Printing tables side by side with help of AI
        String[] table1Lines = restaurant.get(0).split("\\r?\\n");
        String[] table2Lines = restaurant.get(1).split("\\r?\\n");
        String[] table3Lines = restaurant.get(2).split("\\r?\\n");

        int maxHeight = Math.max(
            table1Lines.length,
            Math.max(table2Lines.length, table3Lines.length));
        System.out.println(
            "\u001B[33m" + tableIDs.get(0)
                + "                                     " + tableIDs.get(1)
                + "                                   " + tableIDs.get(2)
                + "\u001B[0m");

        for (int i = 0; i < maxHeight; i++)
        {
            String line1 = (i < table1Lines.length) ? table1Lines[i] : "";
            String line2 = (i < table2Lines.length) ? table2Lines[i] : "";
            String line3 = (i < table3Lines.length) ? table3Lines[i] : "";

            System.out.printf("%-35s %-35s %-35s%n", line1, line2, line3);

        }
        System.out.println();
        String[] table4Lines = null;
        String[] table5Lines = null;
        String[] table6Lines = null;
        if (restaurant.size() > 3)
        {
            table4Lines = restaurant.get(3).split("\\r?\\n");
        }
        if (restaurant.size() > 4)
        {
            table5Lines = restaurant.get(4).split("\\r?\\n");

        }
        if (restaurant.size() > 5)
        {
            table6Lines = restaurant.get(5).split("\\r?\\n");
        }

        int maxHeight2 = 0;
        if (restaurant.size() == 6)
        {
            maxHeight2 = Math.max(
                table4Lines.length,
                Math.max(table5Lines.length, table6Lines.length));
            System.out.println(
                "\u001B[33m" + tableIDs.get(3)
                    + "                                     " + tableIDs.get(4)
                    + "                                   " + tableIDs.get(5)
                    + "\u001B[0m");

        }
        else if (restaurant.size() == 5)
        {
            maxHeight2 = Math.max(table4Lines.length, table5Lines.length);
            System.out.println(
                "\u001B[33m" + tableIDs.get(3)
                    + "                                     " + tableIDs.get(4)
                    + "\u001B[0m");
        }
        else if (restaurant.size() == 4)
        {
            maxHeight2 = table4Lines.length;
            System.out.println("\u001B[33m" + tableIDs.get(3) + "\u001B[0m");

        }

        String line1 = "";
        String line2 = "";
        String line3 = "";

        for (int i = 0; i < maxHeight2; i++)
        {
            if (restaurant.size() > 3)
                line1 = (i < table4Lines.length) ? table4Lines[i] : "";
            if (restaurant.size() > 4)
                line2 = (i < table5Lines.length) ? table5Lines[i] : "";
            if (restaurant.size() > 5)
                line3 = (i < table6Lines.length) ? table6Lines[i] : "";

            System.out.printf("%-35s %-35s %-35s%n", line1, line2, line3);
        }
        System.out.println();

    }


    /**
     * Combines two tables into one. The combined table takes the lower id and
     * the sum of both tables' seats. The original tables are kept so they can
     * be separated later.
     *
     * @param tableID1
     *            id of the first table
     * @param tableID2
     *            id of the second table
     * @return the combined table
     * @throws IllegalArgumentException
     *             if the ids are the same or a table does not exist
     * @throws IllegalStateException
     *             if either table is already combined
     */
    public Table combineTables(int tableID1, int tableID2)
    {
        if (tableID1 == tableID2)
        {
            throw new IllegalArgumentException(
                "Please enter two different tables");
        }
        Table first = getTable(tableID1);
        Table second = getTable(tableID2);
        if (first.isCombined() || second.isCombined())
        {
            throw new IllegalStateException("Table is already combined");
        }
        Table low = first;
        Table high = second;
        if (second.getTableID() < first.getTableID())
        {
            low = second;
            high = first;
        }
        Table combined =
            new Table(nextCombinedID(), low.getNumSeats() + high.getNumSeats());
        combined.setParts(low, high);

        if (combinedTableArray == null)
        {
            combinedTableArray = new ArrayList<Table>(tableArray);
        }
        combinedTableArray.remove(low);
        combinedTableArray.remove(high);
        combinedTableArray.add(combined);
        combinedTableArray.sort((a, b) -> a.getTableID() - b.getTableID());
        return combined;
    }


    /** @return the smallest unused id above the original tables (7, 8, 9...) */
    private int nextCombinedID()
    {
        int id = tableArray.size() + 1;
        while (contains(id))
        {
            id++;
        }
        return id;
    }


    /**
     * Finds a table in the current list without throwing
     *
     * @param tableID
     *            id of the table
     * @return the table, or null if it is not in the current list
     */
    public Table findTable(int tableID)
    {
        for (Table table : getTables())
        {
            if (table.getTableID() == tableID)
            {
                return table;
            }
        }
        return null;
    }


    /**
     * Separates a combined table back into its two original tables. When no
     * combined tables remain, the display goes back to the original list.
     *
     * @param tableID
     *            id of the combined table
     * @throws IllegalArgumentException
     *             if the table is not combined
     */
    public void separateTables(int tableID)
    {
        Table combined = getTable(tableID);
        if (!combined.isCombined())
        {
            throw new IllegalArgumentException("Table is not combined");
        }
        combinedTableArray.remove(combined);
        combinedTableArray.add(combined.getParts()[0]);
        combinedTableArray.add(combined.getParts()[1]);
        combinedTableArray.sort((a, b) -> a.getTableID() - b.getTableID());

        boolean anyCombined = false;
        for (Table t : combinedTableArray)
        {
            if (t.isCombined())
            {
                anyCombined = true;
            }
        }
        if (!anyCombined)
        {
            combinedTableArray = null;
        }
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
     * adds all of the total seats of the table array
     * 
     * @return int total table seats in restaurant
     */
    public int totalSeats()
    {
        int numSeats = 0;
        for (Table table : tableArray)
        {
            numSeats += table.getNumSeats();
        }
        return numSeats;
    }
}
