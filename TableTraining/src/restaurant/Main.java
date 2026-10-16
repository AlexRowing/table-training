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
        while (isOpen())
        {
            System.out.println();
            System.out.println("Current time: " + getFormattedTime());
            printMenu();
            System.out.print("Choose an option: ");
            String choice = handleInput().trim();
            /**
             * Prints the menu of options for the user and checks if the user
             * input is within the options of the menu
             */
            if (!choice.matches("[1-8]") && !choice.matches("cancel"))
            {
                System.out.println("Please enter a number from 1 to 8.");

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
        for (Table t : display.getTables())
            t.setReserved(false, null);

        int now = Reservation.toMinutes(currentHour, currentMinute);
        Node<Party> curr = seating.getWaitList().getHead();
        while (curr != null)
        {
            if (curr.data() instanceof Reservation)
            {
                Reservation r = (Reservation)curr.data();
                if (r.hasHeldTable() && r.minutesUntil(now) <= 60)
                    display.getTable(r.getHeldTable())
                        .setReserved(true, r.getName());
            }
            curr = curr.next();
        }
    }


    /** prints the title at the beginning of the program */
    public void printTitle()
    {
        System.out.println(
            "___________     ___.   .__           ___________         "
                + "    .__       .__\n"
                + "\\__    ___/____ \\_ |__ |  |   ____   \\__    ___/"
                + "___________  |__| ____ |__| ____    ____\n"
                + "  |    |  \\__  \\ | __ \\|  | _/ __ \\    |    |  "
                + "\\_  __ \\__  \\ |  |/    \\|  |/    \\  / ___\\\n"
                + "  |    |   / __ \\| \\_\\ \\  |_\\  ___/    |    |   "
                + "|  | \\// __ \\|  |   |  \\  |   |  \\/ /_/  >\n"
                + "  |____|  (____  /___  /____/\\___  >   |____|   |__|"
                + "  (____  /__|___|  /__|___|  /\\___  /\n"
                + "               \\/    \\/          \\/                  "
                + "      \\/        \\/        \\//_____/");
    }


    /** prints the menu in the beginning of each loop */
    public void printMenu()
    {
        System.out.println(
            " ____________________________\n"
                + "|          Host Menu:        |\n"
                + "|----------------------------|\n"
                + "| 1. Wait List               |\n"
                + "| 2. Add Party               |\n"
                + "| 3. Cancel Party            |\n"
                + "| 4. Add Reservation         |\n"
                + "| 5. Cancel Reservation      |\n"
                + "| 6. Clear Table             |\n"
                + "| 7. Seat Table              |\n"
                + "| 8. Pass Time               |\n"
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
            String tablePrint = "";

            tablePrint += display.toString();

            System.out.println(tablePrint);
            System.out.println(seating.getWaitList());
        }
        /** User input for add Party */
        if (userInput.equals("2"))
        {
            boolean valid = false;
            System.out.println("What is the name of the party");
            String partyName = input.nextLine().trim();
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
            while (!valid)
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
                valid = true;
            }
            seating.addParty(partyName, partySize);

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

                    if (!seating.contains(partyName, partySize))
                    {
                        System.out.println(
                            "That party doesn't exist. Starting over: ");
                        continue restart;
                    }

                    valid = true;
                }

                seating.removeParty(partyName, partySize);
                done = true;

            }

        }
        if (userInput.equals("4"))
        {
            boolean valid1 = false;
            boolean valid2 = false;
            System.out.println("What is the name of the reservation");
            String resName = input.nextLine().trim();
            if (isCancel(resName))
            {
                return "cancel";
            }
            System.out.println("What is the size of the reservation");
            String resTempSize = input.nextLine().trim();
            if (isCancel(resTempSize))
            {
                return "cancel";
            }
            int resSize = 0;
            while (!valid1)
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
                valid1 = true;
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
            exit: while (!valid2)
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
                if (resHour >= 21 && resMinute > 0)
                {
                    System.out.println("Reservations must be before 9:01 pm");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (resHour < 11)
                {
                    System.out.println("Reservations must be after 11:00 am");
                    resTime = input.nextLine().trim();
                    if (isCancel(resTime))
                    {
                        return "cancel";
                    }
                    continue;
                }
                if (resMinute > 0 && resMinute % 10 != 0)
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
                if (resHour < currentHour)
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

                Node<Party> curr = seating.getWaitList().getHe