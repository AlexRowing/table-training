package restaurant;

/**
 * A group of guests waiting to be seated. Walk-ins are plain Parties, and
 * Reservation extends this to add a booked time.
 *
 * @author Adarsh Nambiar
 * @version 2026.10.15
 */
public class Party
{
    // ~ Fields ................................................................

    private String name;
    private int size;

    // ~ Constructors ..........................................................

    /**
     * Creates a new party.
     *
     * @param name
     *            the name the party is under
     * @param size
     *            how many people are in the party
     */
    public Party(String name, int size)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name can't be empty");
        }
        if (size < 1)
        {
            throw new IllegalArgumentException("Party size must be at least 1");
        }
        this.name = name.trim();
        this.size = size;
    }

    // ~Public Methods ........................................................


    /**
     * Gets the name the party is under.
     *
     * @return the party's name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Gets how many people are in the party.
     *
     * @return the party size
     */
    public int getSize()
    {
        return size;
    }


    /**
     * Checks if the whole party can sit at a table with this many seats.
     *
     * @param seats
     *            the table's seating capacity
     * @return true if everyone fits
     */
    public boolean fitsAt(int seats)
    {
        return size <= seats;
    }


    /**
     * Two parties are equal if they have the same name and size.
     *
     * @param obj
     *            the object to compare to
     * @return true if obj is the same party
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        // getClass() so a walk-in never matches a Reservation
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        Party other = (Party)obj;
        return size == other.getSize()
            && name.equalsIgnoreCase(other.getName());
    }


    /**
     * Gets a hash code that matches equals().
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        // lowercase since equals() ignores case
        return 31 * name.toLowerCase().hashCode() + size;
    }


    /**
     * Gets the party as a string, like "Smith (party of 4)".
     *
     * @return the party's name and size
     */
    @Override
    public String toString()
    {
        return name + " (party of " + size + ")";
    }

}
