package restaurant;

import java.util.Scanner;

public class Main
{
    // ~ Fields ................................................................
    int currentTime;
    Seating seating = new Seating();
    Display display = new Display();
    private Scanner input;

    // ~ Constructors ..........................................................
    public Main()
    {
        input = new Scanner(System.in);
        for (int i = 1; i < 7; i++)
        {
            int j = (int)Math.random() * (5) + 2;
            Table table = new Table(i, j);
            display.addTable(table);
        }
    }
    // ~Public Methods ........................................................
    public int getTime() {
        return currentTime;
    }
    
    public void handleInput(Object args) {
        String userInput = input.nextLine();
        
        if (userInput.equals("1")) {
            System.out.println(seating.getWaitList());
        }
        if (userInput.equals("2")) {
            System.out.println("What is the size of the party");
            userInput = input.nextLine();
            //
        }
        if (userInput.equals("3")) {
            System.out.println("What is the size of the party");
            userInput = input.nextLine();
        }
    }
    
    
}
