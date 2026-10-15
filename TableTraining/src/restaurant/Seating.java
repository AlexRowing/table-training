package restaurant;

import java.util.ArrayList;

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
    public void addParty(String name, int size)
    {
        Party party = new Party(name, size);
        waitList.add(party);
    }


    public void removeParty(String name, int size)
    {
        Party party = new Party(name, size);
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


    public LinkedChain<Party> getWaitList()
    {
        return waitList;
    }


    public boolean contains(String name, int size)
    {
        Party party = new Party(name, size);
        return waitList.contains(party);
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
            Party party = new Party("", i);
            if (waitList.contains(party))
            {
                return waitList.remove(party);
            }
        }
        throw new IllegalArgumentException("Invalid party size, try again");
    }


    public void seatTables(ArrayList<Table> tableArray)
    {
        ArrayList<Table> seatingArray = new ArrayList<Table>();
        for (Table table : tableArray)
        {
            if (table.isEmpty() && !table.isReserved())
            {
                seatingArray.add(table);
            }
        }
        for (int i = 0; i < seatingArray.size() - 1; i++)
        {
            int minIndex = i;
            for (int j = i + 1; j < seatingArray.size(); j++)
            {
                if (seatingArray.get(j).getNumSeats() < seatingArray
                    .get(minIndex).getNumSeats())
                {
                    minIndex = j;
                }
            }
            // Swap the found min with the current position
            Table temp = seatingArray.get(i);
            seatingArray.set(i, seatingArray.get(minIndex));
            seatingArray.set(minIndex, temp);
        }
        while (seatingArray.size() != 0)
        {
            for (Table table : seatingArray)
            {
                Party tempParty = compareParties(table.getNumSeats());
                table.setNumPeople(tempParty.getNumPeople());
                table.setEmpty(false);
                seatingArray.remove(seatingArray.indexOf(table));
            }

        }
    }

}
