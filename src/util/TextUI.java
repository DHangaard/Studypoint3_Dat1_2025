package util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextUI {

    // Attributes
    Scanner scanner;

    // Constructor
    public TextUI(){
        scanner = new Scanner(System.in);
    }

    // Methods
    // Display Methods
    public void displayMessage(String message){
        System.out.println(message);
    }


    // Review this method
    public void displayList(ArrayList<String>list, String msg) {
        for (int i = 0; i < list.size();i++) {
            displayMessage(i+1+". "+list.get(i)); // Use displayMessage
        }
    }


    // Prompt methods
    public String promptText(String message){
        displayMessage(message);
        String input = scanner.nextLine();

        return input;
    }


    public int promptInteger(String message){
        int input = 0;
        boolean keepGoing = true;

        while (keepGoing) {
            displayMessage(message);

            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Flush
                keepGoing = false;

            } catch (InputMismatchException e) {
                scanner.nextLine(); // Flush - flush in catch to avoid infinite loop
                displayMessage("[ERROR] " + e);
            }
        }
        return input;
    }


    public double promptDouble(String message){
        double input = 0;
        boolean keepGoing = true;

        while (keepGoing) {
            displayMessage(message);

            try {
                input = scanner.nextDouble();
                scanner.nextLine(); // Flush
                keepGoing = false;

            } catch (InputMismatchException e) {
                scanner.nextLine(); // Flush - flush in catch to avoid infinite loop
                displayMessage("[ERROR] " + e);
            }
        }
        return input;
    }


    public LocalDate promptBirthday(String message) {

        displayMessage(message);

        int year = promptInteger("Fødselsår (YYYY): ");
        int month = promptInteger("Fødselsmåned (MM): ");
        int day = promptInteger("Fødselsdag (DD): ");

        LocalDate birthday = LocalDate.of(year,month,day);

        return birthday;
    }


    public boolean promptBinary(String message){
        String choice = promptText(message);
        if (choice.equalsIgnoreCase("y")){
            return true;
        } else if (choice.equalsIgnoreCase("n")){
            return false;
        } else {
            promptBinary("[ERROR] Input either 'y' or 'n': ");
        }
        return false;
    }


    // Should this be in TextIO? Maybe to specific
    public ArrayList<String> promptChoice( ArrayList<String> options, int limit, String msg){

        displayList(options, "");

        ArrayList<String> choices = new ArrayList<>();  //Lave en beholder til at gemme brugerens valg

        while(choices.size() < limit){             //tjekke om brugeren skal vælge flere drinks

            int choice = promptInteger(msg);
            choices.add(options.get(choice-1));
        }
        return choices;
    }
}
