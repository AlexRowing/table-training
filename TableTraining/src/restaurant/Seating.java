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


    public
        void
        addReservation(String name, int size, int hour, int minute, int TableID)
    {
        Party reservation = new Reservation(name, size, hour, minute);
        Reservation res = (Reservation)reservation;
        res.holdTable(TableID);
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


    // Overloaded
    public boolean contains(String name, int size, int hour, int minute)
    {
        Party reservation = new Reservation(name, size, hour, minute);
        return waitList.contains(reservation);
    }


    @SuppressWarnings("null")
    public Party compareParties(int size)
    {
        Party party = null;
        for (int i = size; i > 0; i--)
        {
            Node<Party> curr = waitList.getHead();
            while (curr != null)
            {
                if (!(curr.data() instanceof Reservation)
                    && curr.data().getSize() == i)
                {
                    party = curr.data();
                    party = waitList.remove(party);
                    return party;
                }
                curr = curr.next();
            }

        }
        return party;
    }


    public void seatTables(ArrayList<Table> tableArray)
    {
        ArrayList<Table> seatingArray = new ArrayList<Table>();
        int currTime =
            Reservation.toMinutes(Main.currentHour, Main.currentMinute);
        for (Table table : tableArray)
        {
            Node<Party> curr = waitList.getHead();
            if (table.isEmpty() && table.isReserved())
            {
                while (curr != null)
                {
                    if (curr.data() instanceof Reservation)
                    {
                        Reservation res = (Reservation)curr.data();
                        if (table.getTableID() == res.getHeldTable()
                            && res.isDue(currTime))
                        {
                            table.setNumPeople(res.getSize());
                            table.setEmpty(false);
                            try
                            {
                                table.setParty(waitList.remove(res));

                            }
                            catch (Exception e)
                            {
                                // Does nothing
                            }
                            break;
                        }
                    }
                    curr = curr.next();
                }
            }
        }

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
        for (Table table : seatingArray)
        {
            Party party = compareParties(table.getNumSeats());
            if (party != null)
            {
                table.setNumPeople(party.getSize());
                table.setParty(party);
                table.setEmpty(false);
            }
        }
    }
}
