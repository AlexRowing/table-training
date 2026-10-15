package restaurant;

import java.util.NoSuchElementException;

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


    public void addReservation(int size, int time)
    {
        Party reservation = new Reservation(size, time);
        waitList.add(reservation);
    }


    public void removeReservation(int size, int time)
    {
        Party reservation = new Reservation(size, time);
        waitList.remove(reservation);
    }


    public boolean contains(int size)
    {
        Party party = new Party(size);
        return waitList.contains(party);
    }


    @Overloaded
    public boolean contains(int size, int time)
    {
        Party reservation = new Reservation(size, time);
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
        throw new NoSuchElementException();
    }
    
    public void seatTables(int size) {
        Party temp = compareParties(size);   
    }

}
