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
    private Scanner input;

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


    public void handleInput(Object args)
    {
        String userInput = input.nextLine();

        if (userInput.equals("1"))
        {
            System.out.println(seating.getWaitList());
        }
        if (userInput.equals("2"))
        {
            System.out.println("What is the size of the party");
            int partySize = input.nextInt();
            int totalSeats = display.totalSeats();
            if (partySize <= totalSeats && partySize > 0)
            {
                seating.addParty(partySize);
            }
            else
            {
                throw new IllegalArgumentException(
                    "Party is too large. Please" + " try again");
            }

        }
        if (userInput.equals("3"))
        {
            System.out.println("What is the size of the party");
            int partySize = input.nextInt();
            if (partySize > 0 && seating.contains(partySize))
            {
                seating.removeParty(input.nextInt());
            }
            else
            {
                throw new IllegalArgumentException(
                    "No party has that size"
                        + " int the waitList. Please try again");
            }

        }
        if (userInput.equals("4"))
        {
           System.out.println("What is the name of the reservation");
            String resName = input.nextLine();
            System.out.println("What is the size of the reservation");
            int resSize = input.nextInt();
            System.out.println("What time is the reservation");
            String resTime = input.nextLine();
            boolean valid = false;
            int resHour = 0;
            int resMinute = 0;
            while(!valid) {
                String[] timeParts = resTime.split(":");
                if (timeParts.length != 2) {
                    System.out.println("Please input a valid time");
                    resTime = input.nextLine();
                    continue;
                }
                if (timeParts[1].length() == 1) {
                    timeParts[1] += "0";
                }
                if (timeParts[0].length() > 2 || timeParts[1].length() > 2) {
                    System.out.println("Please enter a valid time");
                    resTime = input.nextLine();
                    continue;
                }
                try {
                    resHour = Integer.parseInt(timeParts[0]);
                    resMinute = Integer.parseInt(timeParts[1]);
                }
                catch (NumberFormatException e) {
                    System.out.println("Please enter a valid time");
                    resTime = input.nextLine();
                    continue;
                    
                }
                valid = true;
            }
            seating.addReservation(resName, resSize, resHour, resMinute);
        }
    }

}
