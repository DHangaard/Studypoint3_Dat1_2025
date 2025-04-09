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

    // Overloaded displayMessage that prints an objects toString()
    public void displayMessage(Object object){
        System.out.println(object.toString());
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

        // int year = promptInteger("Fødselsår (YYYY): ");
        // int month = promptInteger("Fødselsmåned (MM): ");
        // int day = promptInteger("Fødselsdag (DD): ");

        displayMessage(message);

        int year = 0, month = 0, day = 0;

        // Get valid birth year (e.g. between 1900 and current year)
        while (true) {
            displayMessage("indtast dit fødselsår (e.g., 1900 - 2025): ");
            if (scanner.hasNextInt()) {
                year = scanner.nextInt();
                if (year >= 1900 && year <= 2025) {
                    break;
                } else {
                    System.out.println("ugyldigt år. venligst indtast et år mellem 1900 og 2025.");
                }
            } else {
                System.out.println("ugyldigt input. indtast venligst et årstal.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Get valid month (1-12)
        while (true) {
            System.out.print("indtast din fødselsmåned (1-12): ");
            if (scanner.hasNextInt()) {
                month = scanner.nextInt();
                if (month >= 1 && month <= 12) break;
                else System.out.println("ugyldig måned. indtast venligst en værdi mellem 1 og 12.");
            } else {
                System.out.println("ugyldigt input. Venligst indtast et tal .");
                scanner.next();
            }
        }

        // Get valid day based on year and month
        while (true) {
            System.out.print("indtast din fødselsdag: ");
            if (scanner.hasNextInt()) {
                day = scanner.nextInt();
                if (isValidDay(year, month, day)) break;
                else System.out.println("ugyldig dag i månedet og året. prøv igen.");
            } else {
                System.out.println("ugyldig input. venligst indtast et tal.");
                scanner.next();
            }
        }

        System.out.println("din fødselsdag er: " + year + "-" + month + "-" + day);
        LocalDate birthday = LocalDate.of(year,month,day);

        return birthday;
    }


    // Helper function to validate day for given month and year
    public static boolean isValidDay(int year, int month, int day) {
        int[] daysInMonth = {
                31, // Jan
                isLeapYear(year) ? 29 : 28, // Feb
                31, // Mar
                30, // Apr
                31, // May
                30, // Jun
                31, // Jul
                31, // Aug
                30, // Sep
                31, // Oct
                30, // Nov
                31  // Dec
        };

        return day >= 1 && day <= daysInMonth[month - 1];
    }

    // Check if it's a leap year
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }


    public boolean promptBinary(String message){
        boolean isChoosing = true;
        boolean isAnswerYes = false;
        String choice;

        while (isChoosing){
            choice = promptText(message);
            if (choice.equalsIgnoreCase("y")){
                isAnswerYes = true;
                isChoosing = false;

            } else if (choice.equalsIgnoreCase("n")){
                isAnswerYes = false;
                isChoosing = false;

            } else if (!choice.equalsIgnoreCase("y") || !choice.equalsIgnoreCase("n")) {
                displayMessage("[ERROR] indtast enten 'y' eller 'n': ");
            }
        }

        return isAnswerYes;
    }

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
