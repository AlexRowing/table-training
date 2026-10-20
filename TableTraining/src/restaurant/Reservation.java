package restaurant;

// -------------------------------------------------------------------------
/**
 * A Reservation is a Party that has booked a specific time for today. It keeps
 * everything a Party already has (name and party size) and adds the time the
 * guests are expected, plus the table the system is holding for them (if one
 * has been blocked). Reservations go on the same generic wait list (linked
 * chain) as walk-in Parties. Because reservations can be made in any order
 * during the day, the host stand uses isDue(int) and isEarlierThan(Reservation)
 * to move them up in line as their time gets closer. Times are stored as
 * minutes after midnight (for example, 6:30 PM = 1110) so they can be compared
 * and incremented with simple int math, the same way the system advances the
 * current time after each seating action.
 *
 * @author Adarsh Nambiar
 * @version 2026.10.14
 */
public class Reservation
    extends Party
{
    // ~ Fields ................................................................

    /** Earliest hour (24-hour clock) a reservation can be booked for. */
    public static final int OPEN_HOUR = 11;

    /** Hour (24-hour clock) the restaurant stops taking reservations. */
    public static final int CLOSE_HOUR = 22;

    /** Value of the held table when no table is being held. */
    public static final int NO_TABLE = -1;

    private static final int MINUTES_PER_HOUR = 60;
    private static final int HOURS_PER_DAY = 24;

    /** Reservation time, in minutes after midnight. */
    private int reservationTime;

    /** Number of the table blocked for this reservation, or NO_TABLE. */
    private int heldTable;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new Reservation for today. Reservations are only made day of,
     * so only the hour and minute are needed.
     *
     * @param name
     *            the name the reservation is under
     * @param size
     *            the number of guests (must be at least 1)
     * @param hour
     *            the hour of the reservation on a 24-hour clock (for example,
     *            18 for 6 PM)
     * @param minute
     *            the minute of the reservation (0 to 59)
     * @throws IllegalArgumentException
     *             if the size is less than 1 or the time is outside reservation
     *             hours ("not valid reservation")
     */
    public Reservation(String name, int size, int hour, int minute)
    {
        super(name, size);
        if (size < 1 || !isValidTime(hour, minute))
        {
            throw new IllegalArgumentException("not valid reservation");
        }
        reservationTime = toMinutes(hour, minute);
        heldTable = NO_TABLE;
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Gets the reservation time in minutes after midnight.
     *
     * @return the reservation time in minutes after midnight
     */
    public int getTime()
    {
        return reservationTime;
    }


    // ----------------------------------------------------------
    /**
     * Gets the hour of the reservation on a 24-hour clock.
     *
     * @return the reservation hour (0 to 23)
     */
    public int getHour()
    {
        return reservationTime / MINUTES_PER_HOUR;
    }


    // ----------------------------------------------------------
    /**
     * Gets the minute of the reservation.
     *
     * @return the reservation minute (0 to 59)
     */
    public int getMinute()
    {
        return reservationTime % MINUTES_PER_HOUR;
    }


    // ----------------------------------------------------------
    /**
     * Gets the reservation time formatted for the console, such as "6:30 PM".
     * Used for messages like "Table is reserved for time x:xx".
     *
     * @return the formatted reservation time
     */
    public String getFormattedTime()
    {
        return formatTime(reservationTime);
    }


    // ----------------------------------------------------------
    /**
     * Checks whether this reservation's time has arrived, meaning it should
     * move up in line and be seated ahead of walk-ins.
     *
     * @param currentTime
     *            the current time in minutes after midnight
     * @return true if the current time is at or after the reservation time
     */
    public boolean isDue(int currentTime)
    {
        return currentTime >= reservationTime;
    }


    // ----------------------------------------------------------
    /**
     * Gets how many minutes remain until this reservation. A negative value
     * means the reservation time has already passed.
     *
     * @param currentTime
     *            the current time in minutes after midnight
     * @return the minutes until the reservation time
     */
    public int minutesUntil(int currentTime)
    {
        return reservationTime - currentTime;
    }


    // ----------------------------------------------------------
    /**
     * Checks whether this reservation is earlier in the day than another one.
     * Used to order reservations in line by time.
     *
     * @param other
     *            the reservation to compare against
     * @return true if this reservation's time is earlier than the other's
     */
    public boolean isEarlierThan(Reservation other)
    {
        return other != null && reservationTime < other.reservationTime;
    }


    // ----------------------------------------------------------
    /**
     * Records that a table is being blocked (held) for this reservation.
     *
     * @param tableNumber
     *            the number of the table being held
     * @throws IllegalArgumentException
     *             if the table number is negative
     */
    public void holdTable(int tableNumber)
    {
        if (tableNumber < 0)
        {
            throw new IllegalArgumentException("Table doesn't exist");
        }
        heldTable = tableNumber;
    }


    // ----------------------------------------------------------
    /**
     * Releases the table held for this reservation, for example when the
     * reservation is seated or cancelled.
     */
    public void releaseTable()
    {
        heldTable = NO_TABLE;
    }


    // ----------------------------------------------------------
    /**
     * Gets the number of the table held for this reservation.
     *
     * @return the held table number, or NO_TABLE if none is held
     */
    public int getHeldTable()
    {
        return heldTable;
    }


    // ----------------------------------------------------------
    /**
     * Checks whether a table is currently held for this reservation.
     *
     * @return true if a table is being held
     */
    public boolean hasHeldTable()
    {
        return heldTable != NO_TABLE;
    }


    // ----------------------------------------------------------
    /**
     * Two reservations are equal if they are under the same name (ignoring
     * case), for the same number of guests, at the same time. A plain Party is
     * never equal to a Reservation. This lets the wait list find and remove the
     * right entry when a reservation is cancelled, while still allowing
     * duplicates to be stored.
     *
     * @param obj
     *            the object to compare to
     * @return true if obj is an equal Reservation
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        Reservation other = (Reservation)obj;
        return reservationTime == other.reservationTime
            && getSize() == other.getSize()
            && namesMatch(
                getName(),
                other.getName());
    }


    // ----------------------------------------------------------
    /**
     * Gets a hash code consistent with equals().
     *
     * @return the hash code for this reservation
     */
    @Override
    public int hashCode()
    {
        String name = getName() == null ? "" : getName().toLowerCase();
        int result = name.hashCode();
        result = 31 * result + getSize();
        result = 31 * result + reservationTime;
        return result;
    }


    // ----------------------------------------------------------
    /**
     * Gets a readable description for the host stand, such as "Smith (party of
     * 4) - 6:30 PM [table 3 held]".
     *
     * @return a description of this reservation
     */
    @Override
    public String toString()
    {
        String result = getName() + " (reservation for " + getSize() + ") - "
            + getFormattedTime();
        if (hasHeldTable())
        {
            result += " [table " + heldTable + " held]";
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * Checks whether an hour and minute make a valid reservation time: a real
     * clock time that falls within reservation hours (from OPEN_HOUR up to, but
     * not including, CLOSE_HOUR).
     *
     * @param hour
     *            the hour on a 24-hour clock
     * @param minute
     *            the minute
     * @return true if the time is a valid reservation time
     */
    public static boolean isValidTime(int hour, int minute)
    {
        if (hour < 0 || hour >= HOURS_PER_DAY || minute < 0
            || minute >= MINUTES_PER_HOUR)
        {
            return false;
        }
        return hour >= OPEN_HOUR && hour < CLOSE_HOUR;
    }


    // ----------------------------------------------------------
    /**
     * Checks whether an hour and minute make a valid reservation time for
     * today, given the current time. Since reservations are only made day of, a
     * time that has already passed is not valid.
     *
     * @param hour
     *            the hour on a 24-hour clock
     * @param minute
     *            the minute
     * @param currentTime
     *            the current time in minutes after midnight
     * @return true if the time is valid and not already in the past
     */
    public static boolean isValidTime(int hour, int minute, int currentTime)
    {
        return isValidTime(hour, minute)
            && toMinutes(hour, minute) >= currentTime;
    }


    // ----------------------------------------------------------
    /**
     * Converts an hour and minute into minutes after midnight.
     *
     * @param hour
     *            the hour on a 24-hour clock
     * @param minute
     *            the minute
     * @return the time in minutes after midnight
     */
    public static int toMinutes(int hour, int minute)
    {
        return hour * MINUTES_PER_HOUR + minute;
    }


    // ----------------------------------------------------------
    /**
     * Formats a time in minutes after midnight as a 12-hour clock string, such
     * as "6:30 PM". Can also be used to show the current time.
     *
     * @param minutes
     *            the time in minutes after midnight
     * @return the formatted time
     */
    public static String formatTime(int minutes)
    {
        int hour24 = (minutes / MINUTES_PER_HOUR) % HOURS_PER_DAY;
        int minute = minutes % MINUTES_PER_HOUR;
        int hour12 = hour24 % 12 == 0 ? 12 : hour24 % 12;
        String suffix = hour24 < 12 ? "AM" : "PM";
        return String.format("%d:%02d %s", hour12, minute, suffix);
    }


    // ----------------------------------------------------------
    /**
     * Compares two names, ignoring case and handling nulls.
     */
    private static boolean namesMatch(String a, String b)
    {
        if (a == null)
        {
            return b == null;
        }
        return a.equalsIgnoreCase(b);
    }

}
