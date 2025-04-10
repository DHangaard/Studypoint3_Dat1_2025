import util.FileIO;
import util.TextUI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;

public class AccountManager {
    static FileIO io;
    static TextUI ui;
    private HashMap<String,Account> accounts;
    private final String path = "data/user.csv";

    public AccountManager(){
        this.accounts = new HashMap<>();
        this.io = new FileIO();
        this.ui = new TextUI();
        loadUserData();
    }

    public void createAccount(String username, String password, String name, LocalDate birthdate) {
        if(!isUserInSystem(username)) {
            Account acc = new Account(username, password, name, birthdate);
            acc.setAdmin(promptAdmin());
            this.accounts.put(username, acc);
            appendUserData(acc);
            ui.displayMessage(createAccountMessage(acc));
        } else {
            ui.displayMessage("Brugernavn allerede taget. Prøv venligst et nyt");
        }
    }

    public String createAccountMessage(Account account){
        String message = "";

        if (account.getIsAdmin()){
            message = "Brugeren er oprettet som administrator";
        } else {
            message = "Brugeren er oprettet";
        }
        return message;
    }

    public void loadUserData(){
        ArrayList<String> userdata = io.readData(this.path);

        for (String s : userdata) {
            String[] values = s.split(",");
            String username = values[0].trim();
            String password = values[1].trim();
            String name = values[2].trim();
            LocalDate birthdate = LocalDate.parse(values[3].trim());
            boolean isAdmin = Boolean.parseBoolean(values[4].trim());

            // Create HashMap with Account
            Account acc = new Account(username, password, name, birthdate);
            if (isAdmin) {
             acc.setAdmin(true);
            }
            this.accounts.put(username, acc);
        }
    }

    private boolean promptAdmin(){
        if (ui.promptBinary("Skal denne bruger være administrator? (Y/N)")) {
            String password = ui.promptText("Indtast administrator kodeordet");
            return (makeThisAccountAdmin(password));
        }
        return false;
    }

    public boolean makeThisAccountAdmin(String password) {
        String adminPassword = "iAmAdmin-1234";
        if (password.equals(adminPassword)) {
            return true;
        }
        return false;
    }

    public boolean isUserNameAndPasswordCorrect(String userName, String password){
        if (this.accounts.containsKey(userName)) {
            Account acc = this.accounts.get(userName);
            return acc.getPassword().equals(password);
        }
        return false;
    }

    //
    public  void writeSeenByUser(Media media, Account user) {
        try {
            FileWriter writer = new FileWriter("data/userData/seenBy" + user.getUsername() + ".csv", true);


            writer.write(String.join(";", media.toStringcsv() + System.lineSeparator()));


            writer.close();
        } catch (IOException e) {
            System.out.println("problem: " + e.getMessage());
        }
    }

    public ArrayList<Media> loadSeenMediacsv(Account user) {
        ArrayList<String> mediaData = io.readusercsvData("data/userData/seenBy" + user.getUsername() + ".csv");
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
                    int startYear = Integer.parseInt(values[1].trim());
                    String endYear = values[2].trim();

                    //Fjerner mellemrum i genre
                    ArrayList<String> genre = new ArrayList<>();
                    for (String g : values[3].split(",")) {
                        genre.add(g.trim());
                    }
                    String ratingFromFile = values[4].replaceAll("[^0-9.]", "");

                    double rating = Double.parseDouble(ratingFromFile);


                    Series series = new Series(title, startYear, endYear, genre, rating);
                    mediaList.add(series);

                }

            }

        }

        return mediaList;
    }

    public HashMap<String, Account> getAccounts() {
        return this.accounts;
    }

    public ArrayList<String> getNonAdmins() {
        ArrayList<String> userdata = io.readData(this.path);
        ArrayList<String> nonAdminList = new ArrayList<>();

        for (String s : userdata) {
            String[] values = s.split(",");
            String username = values[0].trim();
            boolean isAdmin = Boolean.parseBoolean(values[4].trim());
            if(!isAdmin) {
                nonAdminList.add(username);
            }
        }
        return nonAdminList;
    }

    public void makeAnotherAccountAdmin(String username){
        accounts.get(username).setAdmin(true);
    }

    public boolean isUserInSystem(String username) {
        return this.accounts.containsKey(username);
    }

    public void appendUserData(Account account) {
        io.appendData(account.toString(), path);
    }

    public Account getAccount(String userName) {
        return this.accounts.get(userName);
    }

}
