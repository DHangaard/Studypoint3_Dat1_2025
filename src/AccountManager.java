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

        if (account.getAdmin()){
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
    public void writeSeenByUser(Media media, Account user) {
        try {
            FileWriter writer = new FileWriter("data/userData/seenBy" + user.getUsername() + ".csv", true);


            writer.write(String.join(";", media.toStringcsv() + System.lineSeparator()));


            writer.close();
        } catch (IOException e) {
            System.out.println("problem: " + e.getMessage());
        }
    }

    boolean isUserInSystem(String username) {
        return this.accounts.containsKey(username);
    }

    boolean isUserAdmin(){
    return false;
    }

    public void appendUserData(Account account) {
        io.appendData(account.toString(), path);
    }

    public Account getAccount(String userName) {
        return this.accounts.get(userName);
    }

}
