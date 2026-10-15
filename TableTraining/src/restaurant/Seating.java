package restaurant;

public class Seating
{
    // ~ Fields ................................................................
    private LinkedChain<Party> waitList;

    // ~ Constructors ..........................................................
    public Seating()
    {
        waitList = new LinkedChain<Party>();
    }


    // ~Public Methods ........................................................
    public void addParty(int size)
    {
        Party party = new Party(size);
        waitList.add(party);
    }


    public void removeParty(int size)
    {
        Party party = new Party(size);
        waitList.remove(party);
    }


    public void addReservation(String name, int size, int hour, int minute)
    {
        Party reservation = new Reservation(name, size, hour, minute);
        waitList.add(reservation);
    }


    public void removeReservation(String name, int size, int hour, int minute)
    {
        Party reservation = new Reservation(name, size, hour, minute);
        waitList.remove(reservation);
    }


    public boolean contains(int size)
    {
        Party party = new Party(size);
        return waitList.contains(party);
    }


    public LinkedChain<Party> getWaitList()
    {
        return waitList;
    }


    @Overloaded
    public boolean contains(String name, int size, int hour, int minute)
    {
        Party reservation = new Reservation(name, size, hour, minute);
        return waitList.contains(reservation);
    }


    public Party compareParties(int size)
    {
        for (int i = size; i > 0; i--)
        {
            Party party = new Party(i);
            if (waitList.contains(party))
            {
                return waitList.remove(party);
            }
        }
        throw new IllegalArgumentException("Invalid party size, try again");
    }


    public void seatTables(int size)
    {
        Party temp = compareParties(size);
    }

}
