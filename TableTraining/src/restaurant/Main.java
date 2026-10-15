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
    private static final int OPEN_HOUR = 11;
    private static final int CLOSE_HOUR = 22;
    int currentHour;
    int currentMinute;
    Seating seating = new Seating();
    Display display = new Display();
    Scanner input;

    // ~ Constructors ..........................................................
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
      