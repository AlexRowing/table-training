package restaurant;

import java.util.Scanner;

/**
 * // -------------------------------------------------------------------------
 * /** Write a one-sentence summary of your class here. Follow it with
 * additional details about its purpose, what abstraction it represents, and how
 * to use it.
 * 
 * @author adambergmaier
 * @version Oct 15, 2026
 */
public class Main
{
    // ~ Fields ................................................................
    int currentHour;
    int currentMinute;
    Seating seating = new Seating();
    Display display = new Display();
    Scanner input;

    // ~ Constructors ..........................................................
    public Main()
    {
        input = new Scanner(System.in);
        /**
         * this fills the Display arrayList with 6 tables of random numSeats
         * from 2 to 6
         */
        for (int i = 1; i < 7; i++)
        {
            int j = (int)Math.random() * (5) + 2;
            Table table = new Table(i, j);
            display.addTable(table);
        }
    }


    // ~Public Methods ........................................................
    public String getTime()
    {
        String time = currentHour + ":" + currentMinute;
        return time;
    }

    public Display getDisplay() {
        return display;
    }

    public String handleInput(Object args)
    {
        String userInput = input.nextLine();

        if (userInput.equals("1"))
        {
            System.out.println(seating.getWaitList());
        }
        if (userInput.equals("2"))
        {
            boolean valid = false;
            int totalSeats = display.totalSeats();
            System.out.println("What is the size of the party");
            String partyTempSize = input.nextLine().trim();
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
                    continue;
                }
                if (partySize > totalSeats)
                {
                    System.out.println("Party size is too large. Try again");
                    partyTempSize = input.nextLine().trim();
                    continue;
                }
                if (partySize <= 0)
                {
                    System.out.println(
                        "Party size must be a positive whole number. "
                            + "Please try again");
                    partyTempSize = input.nextLine().trim();
                    continue;
                }
                valid = true;
            }
            seating.addParty(partySize);

        }
        if (userInput.equals("3"))
        {
            boolean valid = false;
            System.out.println("What is the size of the party");
            String partyTempSize = input.nextLine().trim();
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
                    continue;
                }

                if (!seating.contains(partySize))
                {
                    System.out.println(
                        "No party exists with that size. Please try again");
                    partyTempSize = input.nextLine().trim();
                    continue;
                }

                valid = true;
            }

            seating.removeParty(partySize);

        }
        if (userInput.equals("4"))
        {
            boolean valid1 = false;
            boolean valid2 = false;
            System.out.println("What is the name of the reservation");
            String resName = input.nextLine().trim();
            System.out.println("What is the size of the reservation");
            String resTempSize = input.nextLine().trim();
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
                    resTempSize = input.nextLine();
                    continue;
                }
                if (resSize <= 0)
                {
                    System.out.println("Size must be a positive whole number");
                    resTempSize = input.nextLine();
                    continue;
                }
                if (resSize > display.totalSeats())
                {
                    System.out.println("Size is too large. Please try again");
                    resTempSize = input.nextLine();
                    continue;
                }
                valid1 = true;
            }
            System.out.println("What time is the reservation");
            String resTime = input.nextLine();
            int resHour = 0;
            int resMinute = 0;
            while (!valid2)
            {
                String[] timeParts = resTime.split(":");
                if (timeParts.length != 2)
                {
                    System.out.println("Please input a valid time");
                    resTime = input.nextLine();
                    continue;
                }
                if (timeParts[1].length() == 1)
                {
                    timeParts[1] += "0";
                }
                if (timeParts[0].length() > 2 || timeParts[1].length() > 2)
                {
                    System.out.println("Please enter a valid time");
                    resTime = input.nextLine();
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
                    resTime = input.nextLine();
                    continue;

                }
                if (resHour >= 21 && resMinute > 0)
                {
                    System.out.println("Reservations must be before 9:01 pm");
                    resTime = input.nextLine();
                    continue;
                }
                if (resHour < 11)
                {
                    System.out.println("Reservations must be after 11:00 am");
                    resTime = input.nextLine();
                    continue;
                }
                valid2 = true;
            }
            seating.addReservation(resName, resSize, resHour, resMinute);
        }
        if (userInput.equals("5"))
        {
            boolean valid1 = false;
            boolean valid2 = false;
            System.out.println("What is the name of the reservation");
            String resName = input.nextLine().trim();
            System.out.println("What is the size of the reservation");
            String resTempSize = input.nextLine().trim();
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
                    resTempSize = input.nextLine();
                    continue;
                }
                valid1 = true;
            }
            System.out.println("What time is the reservation");
            String resTime = input.nextLine().trim();
            int resHour = 0;
            int resMinute = 0;
            while (!valid2)
            {
                String[] timeParts = resTime.split(":");
                if (timeParts.length != 2)
                {
                    System.out.println("Please input a valid time");
                    resTime = input.nextLine();
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
                    continue;

                }
                if (!seating.contains(resName, resSize, resHour, resMinute))
                {
                    System.out.println("Please enter a valid reservation");
                    resTime = input.nextLine().trim();
                    continue;
                }
                if (!seating.contains(resName, resSize, resHour, resMinute))
                {
                    System.out.println(
                        "Reservation does not exist." + " Please try again");
                    handleInput("5");

                }
                valid2 = true;
            }

            seating.removeReservation(resName, resSize, resHour, resMinute);

        }
        if (userInput.equals("6"))
        {
            System.out.println("What table would you like to clear?");
            String tableTempNum = input.nextLine().trim();
            int tableNum = 0;
            boolean valid = false;
            while (!valid)
            {
                if (tableTempNum.length() > 1)
                {
                    System.out.println("Please enter a valid table id only");
                    tableTempNum = input.nextLine();
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
                    continue;
                }
                if (!display.contains(tableNum))
                {
                    System.out
                        .println("Table id doesnt exist please try again");
                    tableTempNum = input.nextLine();
                    continue;
                }
                if (display.getTable(tableNum).isEmpty())
                {
                    System.out
                        .println("Table is already empty. Please try again");
                    tableTempNum = input.nextLine();
                    continue;
                }
                valid = true;
            }
            display.getTable(tableNum).setEmpty(true);
            display.resetTableTime(tableNum);

        }
        if (userInput.equals("7")) {
            
        }
        return userInput;
    }

}
