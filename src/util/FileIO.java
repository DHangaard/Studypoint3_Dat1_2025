package util;


import java.io.*;
import java.util.ArrayList;
import article.*;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class FileIO {

    public void saveData(ArrayList<String> list, String path, String header) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(header.strip() + System.lineSeparator());
            for (String s : list) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("problem: " + e.getMessage());
        }
    }

    public void appendData(String object, String path) {
        try {
            FileWriter writer = new FileWriter(path, true); // overloaded FileWriter, true activates append mode
            writer.write(object.strip() + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            System.out.println("problem: " + e.getMessage());
        }
    }

    public ArrayList<String> readData(String path) {
        ArrayList<String> data = new ArrayList<>();
        File file = new File(path);
        try {
            Scanner scan = new Scanner(file);
            scan.nextLine();//skip header;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();   //  "tess, 0"
                data.add(line);
            }
        } catch (FileNotFoundException e) {
        }
        return data;
    }



    public ArrayList<String> readusercsvData(String path) {
        ArrayList<String> data = new ArrayList<>();
        File file = new File(path);
        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();   //  "tess, 0"
                data.add(line);
            }
        } catch (FileNotFoundException e) {
        }
        return data;
    }

    public String[] readData(String path, int length) {
        String[] data = new String[length];
        File file = new File(path);
        try {
            //new scanner created
            Scanner scan = new Scanner(file);
            scan.nextLine();//skip header;

            int i = 0;  //counter
            while (scan.hasNextLine()) {
                String line = scan.nextLine();  //String line bliver instansieret som det scaneren har læst
                data[i] = line;                    //information tilføjes til et array
                i++;                             //counter går op
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return data;
    }

    public void userSavedMedia(ArrayList<Media> mediaList, Account user) {
        try {
            FileWriter writer = new FileWriter("data/userData/savedBy" + user.getUsername() + ".csv", true);
            for (Media s : mediaList) {
                writer.write(s.toString().strip() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("problem: " + e.getMessage());
        }
    }


    public void writeSavedByUser(Media media, Account user) {
        try {
            FileWriter writer = new FileWriter("data/userData/savedBy" + user.getUsername() + ".csv", true);


            writer.write(String.join(";", media.toString().strip() + System.lineSeparator()));


            writer.close();
        } catch (IOException e) {
            System.out.println("problem: " + e.getMessage());
        }
    }
    public void writeSeenByUser(Media media, Account user) {
        try {
            FileWriter writer = new FileWriter("data/userData/seenBy" + user.getUsername() + ".csv", true);


                writer.write(String.join(";", media + System.lineSeparator()));


            writer.close();
        } catch (IOException e) {
            System.out.println("problem: " + e.getMessage());
        }
    }

    public ArrayList<Media> loadSeenMediacsv(Account user) {
        ArrayList<String> mediaData = readusercsvData("data/userData/seenBy" + user.getUsername() + ".csv");
        ArrayList<Media> mediaList = new ArrayList<>();


        for (String s : mediaData) {
            String[] parts = s.split("\n");
            for (String part : parts) {
                String[] values = part.split(";");
                if (values.length < 4) {
                    String title = values[0].trim();
                    int year = Integer.parseInt(values[1].replaceAll("[^0-9.]", ""));

                    //Fjerner mellemrum i genre
                    ArrayList<String> genre = new ArrayList<>();
                    for (String g : values[2].split(",")) {
                        genre.add(g.trim());
                    }

                    double rating = Double.parseDouble(values[3].replaceAll("[^0-9.]", ""));

                    Movie movie = new Movie(title, year, genre, rating);
                    mediaList.add(movie);

                } else {

                    String title = values[0].trim();
                    String year = values[1].trim();

                    //Fjerner mellemrum i genre
                    ArrayList<String> genre = new ArrayList<>();
                    for (String g : values[2].split(",")) {
                        genre.add(g.trim());
                    }
                    String ratingFromFile = values[3].replaceAll("[^0-9.]", "");

                    double rating = Double.parseDouble(ratingFromFile);


                    Series series = new Series(title, year, genre, rating);
                    mediaList.add(series);

                }

            }

        }

        return mediaList;
    }
}
