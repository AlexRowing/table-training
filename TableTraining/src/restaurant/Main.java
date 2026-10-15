package restaurant;

import java.util.Scanner;

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
    int currentHour;
    /** minutes of the current time in the program */
    int currentMinute;
    /** Creates the seating object to be called for logic */
    Seating seating = new Seating();
    /** Creates the display object to access the tables and display tables */
    Display display = new Display();
    /** input takes the input provided by the user */
    Scanner input;

    // ~ Constructors .................