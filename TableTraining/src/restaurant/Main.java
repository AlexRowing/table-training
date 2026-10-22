package restaurant;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main class implements the display object and seating object. The main class
 * handles input through the static void main method and while the time in the
 * program is between 11:00 am and 10:00 pm.
 * 
 * @author adambergmaier
 * @version Oct 15, 2026
 */
public class Main
{
    // ~ Fields ................................................................
    /** The earliest time that the program can run */
    private static final int OPEN_HOUR = 11;
    /** The latest hour the program can run */
    private static final int CLOSE_HOUR = 22;
    /** current hour within the program */
    static int currentHour;
    /** minutes of the current time in the program */
    static int currentMinute;
    /** Creates the seating object to be called for logic */
    Seating seating = new Seating();
    /** Creates the display object to access the tables and display tables */
    Display display = new Display();
    /** input takes the input provided by the user */
    Scanner input;

    // ~ Constructors ..........................................................
    /**
     * Constructor of the main class to be initialized at the beginning of the
     * program
     */
    public Main()
    {
        input = new Scanner(System.in);
        currentHour = OPEN_HOUR;
        currentMinute = 0;
        /**
         * this fills the Display arrayList with 6 tables of random numSeats
         * from 2 to 6
         */
        for (int i = 1; i < 7; i++)
        {
            int j = (int)(Math.random() * 5) + 2;
            Table table = new Table(i, j);
            display.addTable(table);
        }
    }


    // ~Public Methods ........................................................
    /**
     * Allows the program and main.java to be ran as a java application
     * 
     * @param args
     *            is the collection of all the user inputs
     */
    public static void main(String[] args)
    {
        Main main = new Main();
        main.run();
    }


    /** The loop that keeps the program running until the restaurant closes */
    public void run()
    {
        printTitle();
        System.out.println("Hours of operation: 11:00 am - 10:00 pm");
        while (isOpen())
        {
            updateReservedTables();
            System.out.println();
            display.showTablesVisually();
            System.out.println("Current time: " + getFormattedTime());
            printMenu();
            System.out.print("Choose an option: ");
            String choice = handleInput().trim();
            /**
             * Prints the menu of options for the user and checks if the user
             * input is within the options of the menu
             */
            if (!choice.matches("[1-9]") && !choice.matches("cancel"))
            {
                System.out.println("Please enter a number from 1 to 9.");

            }
        }
        System.out.println("The restaurant is now closed. Great Job!");
    }


    /**
     * increments time by 10 minutes, and updates the reserved tables for that
     * hour
     */
    private void passTime()
    {
        if (currentMinute == 50)
        {
            currentMinute = 0;
            currentHour += 1;
        }
        else
        {
            currentMinute += 10;
        }
        display.updateTableTimes();
        updateReservedTables();
    }


    /** updates the table objects isReserved method */
    private void updateReservedTables()
    {
        int now = Reservation.toMinutes(currentHour, currentMinute);
        ArrayList<Integer> toSeparate = new ArrayList<Integer>();

        // 1. auto-clear tables that have been sat for 60 minutes
        for (Table t : display.getTables())
        {
            if (t.getTimeSat() >= 60)
            {
                t.setEmpty(true);
                t.setNumPeople(0);
                t.setTimeSat(0);
                if (t.isCombined() && !seating.hasReservationOn(t.getTableID()))
                {
                    toSeparate.add(t.getTableID());
                    System.out.println(
                        t.getLabel() + " has left and is now separated");
                }
                else
                {
                    System.out.println(
                        t.getLabel() + " has left and is now clear to be sat");
                }
            }
        }

        // 2. empty combined tables with no reservation of their own
        for (Table t : display.getTables())
        {
            if (t.isCombined() && t.isEmpty()
                && !toSeparate.contains(t.getTableID())
                && !seating.hasReservationOn(t.getTableID()))
            {
                int earliest = seating.earliestReservationOn(
                    t.getParts()[0].getTableID(),
                    t.getParts()[1].getTableID());
                if (earliest != -1)
                {
                    int until = earliest - now;
                    if (until == 60)
                    {
                        Party p = seating.compareParties(t.getNumSeats());
                        if (p != null)
                        {
                            t.setNumPeople(p.getSize());
                            t.setParty(p);
                            t.setEmpty(false);
                            System.out.println(
                                "Party for " + p.getSize()
                                    + " was automatically sat at "
                                    + t.getLabel());
                        }
                        else
                        {
                            toSeparate.add(t.getTableID());
                            System.out.println(
                                t.getLabel() + " had no walk-in to seat and "
                                    + "is now separated");
                        }
                    }
                    else if (until < 60)
                    {
                        toSeparate.add(t.getTableID());
                        System.out.println(t.getLabel() + " is now separated");
                    }
                }
            }
        }
        for (int id : toSeparate)
        {
            display.separateTables(id);
        }

        // 3. wipe flags, 4. rebuild them
        for (Table t : display.getTables())
        {
            t.setReserved(false, null);
        }
        Node<Party> curr = seating.getWaitList().getHead();
        while (curr != null)
        {
            if (curr.data() instanceof Reservation)
            {
                Reservation r = (Reservation)curr.data();
                if (r.hasHeldTable() && r.minutesUntil(now) <= 60)
                {
                    Table held = display.findTable(r.getHeldTable());
                    if (held != null)
                    {
                        held.setReserved(true, r.getName());
                    }
                }
            }
            curr = curr.next();
        }
    }


    /** prints the title at the beginning of the program */
    public void printTitle()
    {
        System.out.println(
            "\u001B[31m" + "___________     ___.  " + "\u001B[33m"
                + " .__           _____" + "\u001B[32m" + "______"
                + "\u001B[32m" + "         " + "\u001B[34m"
                + "    .__       .__\n" + "\u001B[31m"
                + "\\__    ___/____ \\_ |__ "
                + "\u001B[33m|  |   ____   \\__    " + "\u001B[32m"
                + "___/___________" + "\u001B[34m" + "  |__| ____ |__| "
                + "\u001B[35m" + "____    ____\n" + "\u001B[31m"
                + "  |    |  \\__  \\ |" + "\u001B[33m" + " __" + "\u001B[33m"
                + " \\|  | _/ __ \\    " + "\u001B[32m" + "|    " + "\u001B[32m"
                + "|  \\_  __ \\__  " + "\u001B[34m" + "\\ |  |/    \\|  |"
                + "\u001B[35m" + "/    \\  / ___\\\n" + "\u001B[31m"
                + "  |    |   / __ \\| " + "\u001B[33m"
                + "\\_\\ \\  |_\\  ___/    " + "\u001B[32m"
                + "|    |   |  | \\// " + "\u001B[34m" + "__"
                + " \\|  |   |  \\  " + "\u001B[35m" + "|   |  \\/ /_/  >\n"
                + "\u001B[31m" + "  |____|  (____  " + "\u001B[33m"
                + "/___  /____/\\___  " + "\u001B[32m" + ">   " + "\u001B[32m"
                + "|____|   |__|" + "  (" + "\u001B[34m" + "____  /__|___|  /"
                + "\u001B[35m" + "__|___|  /\\___  /\n" + "\u001B[31m"
                + "               \\" + "\u001B[33m" + "/    \\/          "
                + "\u001B[32m" + "\\/                  " + "\u001B[35m"
                + "      " + "\u001B[34m" + "\\/        \\/        "
                + "\u001B[35m" + "\\//_____/" + "\u001B[0m");
    }


    /** prints the menu in the beginning of each loop */
    public void printMenu()
    {
        System.out.println(
            " ____________________________\n"
                + "|         Host Menu:         |\n"
                + "|----------------------------|\n"
                + "| 1. Wait List               |\n"
                + "| 2. Add Party               |\n"
                + "| 3. Cancel Party            |\n"
                + "| 4. Add Reservation         |\n"
                + "| 5. Cancel Reservation      |\n"
                + "| 6. Clear Table             |\n"
                + "| 7. Seat Table              |\n"
                + "| 8. Pass Time               |\n"
                + "| 9. Combine Tables          |\n"
                + " ---------------------------- ");
    }


    /**
     * @param text
     *            from userInput
     * @return if the text is "cancel"
     */
    private boolean isCancel(String text)
    {
        return text.equalsIgnoreCase("cancel");
    }


    /** @return current time in the program */
    public String getTime()
    {
        String currentTime = "";
        if (currentMinute > 0)
        {
            currentTime = currentHour + ":" + currentMinute;
        }
        else
        {
            currentTime = currentHour + ":" + "00";
        }
        return currentTime;
    }


    /** @return Formats the Time to be printed */
    public static String getFormattedTime()
    {
        String result = "";
        String AmPm = "";
        if (currentHour / 12 != 1)
        {
            AmPm = " AM";
            result += (currentHour % 12);
        }
        else if (currentHour == 12)
        {
            AmPm = " PM";
            result += (currentHour);
        }
        else
        {
            AmPm = " PM";
            result += (currentHour % 12);
        }
        result += ":";
        if (currentMinute == 0)
        {
            result += "0" + currentMinute;
        }
        else
        {
            result += (currentMinute);
        }
        result += AmPm;
        return result;

    }


    /** @return if the restaurant is open */
    public boolean isOpen()
    {
        return currentHour < CLOSE_HOUR;
    }


    /**
     * @return The display object that is stored in the main class. Allows for
     *             other classes to access the display and its methods
     */
    public Display getDisplay()
    {
        return display;
    }


    /**
     * Asks for a table id until an existing one is entered
     *
     * @param prompt
     *            the question to ask
     * @return the table id, or -1 if the user typed cancel
     */
    private int askTableID(String prompt)
    {
        System.out.println(prompt);
        while (true)
        {
            String text = input.nextLine().trim();
            if (isCancel(text))
            {
                return -1;
            }
            try
            {
                int id = Integer.parseInt(text);
                if (display.contains(id))
                {
                    return id;
                }
                System.out.println("Table id doesnt exist please try again");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a valid table id");
            }
        }
    }


    /**
     * Checks whether two tables can be combined
     *
     * @param id1
     *            first table id
     * @param id2
     *            second table id
     * @return the reason they can't be combined, or null if they can
     */
    private String combineProblem(int id1, int id2)
    {
        if (id1 == id2)
        {
            return "Please choose two different tables";
        }
        Table a = display.getTable(id1);
        Table b = display.getTable(id2);
        if (a.isCombined() || b.isCombined())
        {
            return "A combined table can not be combined again";
        }
        if (!a.isEmpty() || !b.isEmpty())
        {
            return "Both tables must be empty to be combined";
        }
        int earliest = seating.earliestReservationOn(id1, id2);
        int now = Reservation.toMinutes(currentHour, currentMinute);
        if (earliest != -1 && earliest - now < 120)
        {
            return "One of those tables has a reservation less than 2 hours "
                + "away. Try different tables";
        }
        return null;
    }


    /**
     * Takes the inputs from the user and calls methods or checks for bad input
     * and prompts the user to try again
     * 
     * @return the most recent valid user input
     */
    @SuppressWarnings("null")
    public String handleInput()
    {
        updateReservedTables();
        String userInput = input.nextLine().trim();
        if (isCancel(userInput))
        {
            return "cancel";
        }
        /** User input for Wait List */
        if (userInput.equals("1"))
        {
            updateReservedTables();
            System.out.println("\n" + seating.getWaitList());
        }
        /** User input for add Party */
        if (userInput.equals("2"))
        {
            boolean valid1 = false;
            boolean valid2 = false;
            boolean nameIsNum;
            System.out.println("What is the name of the party");
            String partyName = input.nextLine().trim();
            if (isCancel(partyName))
            {
                return "cancel";
            }
            while (!valid1)
            {
                if (partyName == null || partyName.isEmpty())
                {
                    System.out.println("Please enter a name for the party");
                    partyName = input.nextLine().trim();
                    if (isCancel(partyName))
                    {
                        return "cancel";
                    }
                    continue;
                }
                try
                {
                    Integer.parseInt(partyName);
                    nameIsNum = true;
                }
                catch (NumberFormatException e)
                {
                    nameIsNum = false;
                }
                if (nameIsNum == true)
                {
                    System.out.println(
                        "Name can not be a number." + " Please try again");
                    partyName = input.nextLine().trim();
                    if (isCancel(partyName))
                    {
                        return "cancel";
                    }
                    continue;
                }
                valid1 = true;

            }
            if (isCancel(partyName))
            {
                return "cancel";
            }
            int totalSeats = display.totalSeats();
            System.out.println("What is the size of the party");
            String partyTempSize = input.nextLine().trim();
            if (isCancel(partyTempSize))
            {
                return "cancel";
            }
            int partySize = 0;
            while (!valid2)
            {
                try
                {
                    partySize = Integer.parseInt(partyTempSize);
                }
                catch (Exception e)
                {
                    System.out.println("Please enter a valid party size");
                    partyTempSize = input.nextLine().trim();
                    if (isCancel(partyTempSize))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (partySize > totalSeats)
                {
                    System.out.println("Party size is too large. Try again");
                    partyTempSize = input.nextLine().trim();
                    if (isCancel(partyTempSize))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (partySize <= 0)
                {
                    System.out.println(
                        "Party size must be a positive whole number. "
                            + "Please try again");
                    partyTempSize = input.nextLine().trim();
                    if (isCancel(partyTempSize))
                    {
                        return "cancel";
                    }
                    continue;
                }
                valid2 = true;
            }
            seating.addParty(partyName, partySize);
            System.out.println(
                "\n" + partyName + "'s party was added to the wait list");

        }
        /** User input for removing a party from wait List */
        if (userInput.equals("3"))
        {
            boolean done = false;
            restart: while (!done)
            {
                boolean valid = false;
                System.out.println("What is the name of the party");
                String partyName = input.nextLine().trim();
                if (isCancel(partyName))
                {
                    return "cancel";
                }
                System.out.println("What is the size of the party");
                String partyTempSize = input.nextLine().trim();
                if (isCancel(partyTempSize))
                {
                    return "cancel";
                }
                int partySize = 0;
                while (!valid)
                {
                    try
                    {
                        partySize = Integer.parseInt(partyTempSize);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Please enter a valid party size");
                        partyTempSize = input.nextLine().trim();
                        if (isCancel(partyTempSize))
                        {
                            return "cancel";
                        }
                        continue;
                    }

                    if (partyName.isEmpty() || partySize <= 0
                        || !seating.contains(partyName, partySize))
                    {
                        System.out.println(
                            "That party doesn't exist. Starting over: ");
                        continue restart;
                    }

                    valid = true;
                }

                seating.removeParty(partyName, partySize);
                done = true;
                System.out.println(
                    "\n" + partyName
                        + "'s party was removed from the wait list");

            }

        }
        if (userInput.equals("4"))
        {
            boolean valid1 = false;
            boolean valid2 = false;
            boolean valid3 = false;
            boolean nameIsNum;
            System.out.println("What is the name of the reservation");
            String resName = input.nextLine().trim();
            if (isCancel(resName))
            {
                return "cancel";
            }
            while (!valid1)
            {
                if (resName == null || resName.isEmpty())
                {
                    System.out
                        .println("Please enter a name for the reservation");
                    resName = input.nextLine().trim();
                    if (isCancel(resName))
                    {
                        return "cancel";
                    }
                    continue;
                }
                try
                {
                    Integer.parseInt(resName);
                    nameIsNum = true;
                }
                catch (NumberFormatException e)
                {
                    nameIsNum = false;
                }
                if (nameIsNum == true)
                {
                    System.out.println(
                        "Name can not be a number." + " Please try again");
                    resName = input.nextLine().trim();
                    if (isCancel(resName))
                    {
                        return "cancel";
                    }
                    continue;
                }
                valid1 = true;

            }
            System.out.println("What is the size of the reservation");
            String resTempSize = input.nextLine().trim();
            if (isCancel(resTempSize))
            {
                return "cancel";
            }
            int resSize = 0;
            while (!valid2)
            {
                try
                {
                    resSize = Integer.parseInt(resTempSize);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Please enter a valid size");
                    resTempSize = input.nextLine().trim();
                    if (isCancel(resTempSize))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (resSize <= 0)
                {
                    System.out.println("Size must be a positive whole number");
                    resTempSize = input.nextLine().trim();
                    if (isCancel(resTempSize))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (resSize > display.totalSeats())
                {
                    System.out.println("Size is too large. Please try again");
                    resTempSize = input.nextLine().trim();
                    if (isCancel(resTempSize))
                    {
                        return "cancel";
                    }
                    continue;
                }
                valid2 = true;
            }
            System.out.println("What time is the reservation");
            String resTime = input.nextLine().trim();
            if (isCancel(resTime))
            {
                return "cancel";
            }
            int resHour = 0;
            int resMinute = 0;
            Table resHTable = null;
            exit: while (!valid3)
            {
                String[] timeParts = resTime.split(":");
                if (timeParts.length != 2)
                {
                    System.out.println("Please input a valid time");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (timeParts[1].length() == 1)
                {
                    timeParts[1] += "0";
                }
                if (timeParts[0].length() > 2 || timeParts[1].length() > 2)
                {
                    System.out.println("Please enter a valid time");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }
                try
                {
                    resHour = Integer.parseInt(timeParts[0]);
                    resMinute = Integer.parseInt(timeParts[1]);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Please enter a valid time");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;

                }
                if (!Reservation.isValidTime(resHour, resMinute)
                    && resHour == 21 && resMinute > 0 || resHour > 21)
                {
                    System.out.println("Reservations must be before 9:01 pm");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (!Reservation.isValidTime(resHour, resMinute)
                    && resHour < 11)
                {
                    System.out.println("Reservations must be after 11:00 am");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (resMinute < 60 && resMinute > 0 && resMinute % 10 != 0)
                {
                    System.out.println(
                        "Minutes must be in multiples of 10. "
                            + "Please try again.");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }
                int now = Reservation.toMinutes(currentHour, currentMinute);
                if (!Reservation.isValidTime(resHour, resMinute, now))
                {
                    System.out.println(
                        "Reservations can not be made for earlier than "
                            + "right now. Please input a valid time");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }

                Node<Party> curr = seating.getWaitList().getHead();
                ArrayList<Table> resTables = new ArrayList<Table>();
                int compareRes = Reservation.toMinutes(resHour, resMinute);
                for (Table table : display.getTables())
                {
                    boolean usable = true;
                    if (table.isCombined())
                    {
                        int earliest = seating.earliestReservationOn(
                            table.getParts()[0].getTableID(),
                            table.getParts()[1].getTableID());
                        if (earliest != -1 && compareRes > earliest - 60)
                        {
                            usable = false;
                        }
                    }
                    if (usable)
                    {
                        resTables.add(table);
                    }
                }
                while (curr != null)
                {
                    if (curr.data() instanceof Reservation)
                    {
                        Reservation currRes = (Reservation)curr.data();
                        if (currRes.hasHeldTable())
                        {
                            if (Math.abs(currRes.getTime() - compareRes) < 60)
                            {
                                for (Table table : resTables)
                                {
                                    if (table.getTableID() == currRes
                                        .getHeldTable())
                                    {
                                        resTables.remove(table);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    curr = curr.next();
                }
                for (int i = 0; i < resTables.size() - 1; i++)
                {
                    int minIndex = i;
                    for (int j = i + 1; j < resTables.size(); j++)
                    {
                        if (resTables.get(j).getNumSeats() < resTables
                            .get(minIndex).getNumSeats())
                        {
                            minIndex = j;
                        }

                    }
                    Table temp = resTables.get(i);
                    resTables.set(i, resTables.get(minIndex));
                    resTables.set(minIndex, temp);
                }
                for (Table table : resTables)
                {
                    if (table.isEmpty())
                    {
                        if (table.getNumSeats() >= resSize)
                        {
                            resHTable = table;
                            valid3 = true;
                            continue exit;
                            // Edit this once combine table gets implemented
                        }
                    }
                    int seatTime = 60 - table.getTimeSat();
                    int currTime =
                        Reservation.toMinutes(currentHour, currentMinute);
                    int resToTime = Reservation.toMinutes(resHour, resMinute);
                    int toTime = resToTime - currTime;
                    if (seatTime - toTime <= 0)
                    {
                        if (table.getNumSeats() >= resSize)
                        {
                            resHTable = table;
                            valid3 = true;
                            continue exit;
                            // Edit this once combine table gets implemented
                        }
                    }

                }
                System.out.println(
                    "Cannot accommodate that reservation. "
                        + "Please try a new time");
                resTime = input.nextLine().trim();
                if (isCancel(resTime))
                {
                    return "cancel";
                }
                continue;
            }
            seating.addReservation(
                resName,
                resSize,
                resHour,
                resMinute,
                resHTable.getTableID());
            System.out.println(
                "\n" + resName + "'s reservation was added to the wait list");
        }
        if (userInput.equals("5"))
        {
            boolean done = false;
            restart: while (!done)
            {
                boolean valid1 = false;
                boolean valid2 = false;
                boolean valid3 = false;
                boolean nameIsNum;
                System.out.println("What is the name of the reservation");
                String resName = input.nextLine().trim();
                if (isCancel(resName))
                {
                    return "cancel";
                }
                while (!valid1)
                {
                    if (resName == null || resName.isEmpty())
                    {
                        System.out.println(
                            "Please enter the name of the reservation");
                        resName = input.nextLine().trim();
                        if (isCancel(resName))
                        {
                            return "cancel";
                        }
                        continue;
                    }
                    try
                    {
                        Integer.parseInt(resName);
                        nameIsNum = true;
                    }
                    catch (NumberFormatException e)
                    {
                        nameIsNum = false;
                    }
                    if (nameIsNum == true)
                    {
                        System.out.println(
                            "Name can not be a number." + " Please try again");
                        resName = input.nextLine().trim();
                        if (isCancel(resName))
                        {
                            return "cancel";
                        }
                        continue;
                    }
                    valid1 = true;

                }
                System.out.println("What is the size of the reservation");
                String resTempSize = input.nextLine().trim();
                if (isCancel(resTempSize))
                {
                    return "cancel";
                }
                int resSize = 0;
                while (!valid2)
                {
                    try
                    {
                        resSize = Integer.parseInt(resTempSize);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Please enter a valid size");
                        resTempSize = input.nextLine().trim();
                        if (isCancel(resTempSize))
                        {
                            return "cancel";
                        }
                        continue;
                    }
                    valid2 = true;
                }
                System.out.println("What time is the reservation");
                String resTime = input.nextLine().trim();
                if (isCancel(resTime))
                {
                    return "cancel";
                }
                int resHour = 0;
                int resMinute = 0;
                while (!valid3)
                {
                    String[] timeParts = resTime.split(":");
                    if (timeParts.length != 2)
                    {
                        System.out.println("Please input a valid time");
                        resTime = input.nextLine().trim();
                        if (isCancel(resTime))
                        {
                            return "cancel";
                        }
                        continue;
                    }
                    if (timeParts[1].length() == 1)
                    {
                        timeParts[1] += "0";
                    }
                    if (timeParts[0].length() > 2 || timeParts[1].length() > 2)
                    {
                        System.out.println("Please enter a valid time");
                        resTime = input.nextLine().trim();
                        if (isCancel(resTime))
                        {
                            return "cancel";
                        }
                        continue;
                    }
                    try
                    {
                        resHour = Integer.parseInt(timeParts[0]);
                        resMinute = Integer.parseInt(timeParts[1]);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Please enter a valid time");
                        resTime = input.nextLine().trim();
                        if (isCancel(resTime))
                        {
                            return "cancel";
                        }
                        continue;

                    }
                    if (!Reservation.isValidTime(resHour, resMinute))
                    {
                        System.out.println("Please enter a valid time");
                        resTime = input.nextLine().trim();
                        if (isCancel(resTime))
                        {
                            return "cancel";
                        }
                        continue;
                    }
                    if (!seating.contains(resName, resSize, resHour, resMinute))
                    {
                        System.out.println(
                            "Reservation does not exist." + " Starting over. ");
                        continue restart;

                    }
                    valid3 = true;
                }

                seating.removeReservation(resName, resSize, resHour, resMinute);
                done = true;
                System.out.println(
                    "\n" + resName
                        + "'s reservation was removed from the wait list");
            }
        }
        if (userInput.equals("6"))
        {
            System.out.println("What table would you like to clear?");
            String tableTempNum = input.nextLine().trim();
            if (isCancel(tableTempNum))
            {
                return "cancel";
            }
            int tableNum = 0;
            boolean valid = false;
            while (!valid)
            {
                if (tableTempNum.length() > 1)
                {
                    System.out.println("Please enter a valid table id only");
                    tableTempNum = input.nextLine().trim();
                    if (isCancel(tableTempNum))
                    {
                        return "cancel";
                    }
                    continue;
                }
                try
                {
                    tableNum = Integer.parseInt(tableTempNum);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Please enter a valid table id");
                    tableTempNum = input.nextLine().trim();
                    if (isCancel(tableTempNum))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (!display.contains(tableNum))
                {
                    System.out
                        .println("Table id doesnt exist please try again");
                    tableTempNum = input.nextLine().trim();
                    if (isCancel(tableTempNum))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (display.getTable(tableNum).isEmpty()
                    && !display.getTable(tableNum).isCombined())
                {
                    System.out
                        .println("Table is already empty. Please try again");
                    tableTempNum = input.nextLine().trim();
                    if (isCancel(tableTempNum))
                    {
                        return "cancel";
                    }
                    continue;
                }
                valid = true;
            }
            Table cleared = display.getTable(tableNum);
            cleared.setEmpty(true);
            cleared.setNumPeople(0);
            display.resetTableTime(tableNum);
            System.out.println("\nClearing " + cleared.getLabel());
            if (cleared.isCombined())
            {
                if (seating.hasReservationOn(tableNum))
                {
                    System.out.println(
                        cleared.getLabel()
                            + " has a reservation and stays combined");
                }
                else
                {
                    System.out
                        .println(cleared.getLabel() + " has been separated");
                    display.separateTables(tableNum);
                }
            }

        }
        // if (table.isEmpty() && table.isReserved()) {
        // seating.seatTables
        // }
        if (userInput.equals("7"))
        {
            ArrayList<Table> wasEmpty = new ArrayList<Table>();
            for (Table table : display.getTables())
            {
                if (table.isEmpty())
                {
                    wasEmpty.add(table);
                }
            }
            seating.seatTables(display.getTables());
            for (Table table : wasEmpty)
            {
                if (!table.isEmpty())
                {
                    if (table.getParty() instanceof Reservation)
                    {
                        System.out.println(
                            "Reservation under "
                                + ((Reservation)table.getParty()).getName()
                                + " sat at " + table.getLabel());
                    }
                    else
                    {
                        System.out.println(
                            "Party for " + table.getNumPeople() + " was sat at "
                                + table.getLabel());
                    }
                }
            }
            passTime();
        }

        if (userInput.equals("8"))

        {

            passTime();
            System.out.println(
                "\n10 minutes have passed. What would you like to do?");
        }

        if (userInput.equals("9"))
        {
            int id1 = askTableID("What is the first table to combine?");
            if (id1 == -1)
            {
                return "cancel";
            }
            int id2 = askTableID("What is the second table to combine?");
            if (id2 == -1)
            {
                return "cancel";
            }
            String problem = combineProblem(id1, id2);
            if (problem != null)
            {
                System.out.println("\n" + problem);
            }
            else
            {
                Table combined = display.combineTables(id1, id2);
                System.out.println(
                    "\n" + combined.getLabel() + " is now combined ("
                        + combined.getNumSeats() + " seats)");
            }
        }
        return userInput;
    }

}
