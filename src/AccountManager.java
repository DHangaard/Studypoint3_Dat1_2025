import util.FileIO;
import util.TextUI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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

    /*
    Public void createAccount(){
        String tmpUsername = ui.promptText("Hvad er dit oenskede brugernavn?");
        String tmpPassword = ui.promptText(Venligst set et password);
        String tmpName = ui.promptText;
        LocalDate tmpBirthdate = ui.promptBirthday;

        if(!isUserInSystem(tmpUsername, tmpPassword)){
            Account acc = new Account(tmpUsername, tmpPassword, tmpName, tmpBirthdate);
            this.accounts.put(username, acc);
            saveUserData(accounts);
        } else {
            ui.displayMessage("Error: Username already taken")
        }
    }
    */

    public void createAccount(String username, String password, String name, LocalDate birthdate) {
        if(!isUserInSystem(username)) {
            Account acc = new Account(username, password, name, birthdate);
            this.accounts.put(username, acc);
            saveUserData();

        } else {
            ui.displayMessage("Error: Username already taken");
        }
    }

    public void loadUserData(){
        ArrayList<String> userdata = io.readData(this.path);

        for (String s : userdata) {
            String[] values = s.split(",");
            String username = values[0].trim();
            String password = values[1].trim();
            String name = values[2].trim();
            LocalDate birthday = LocalDate.parse(values[3].trim());
            //Boolean child = parseBoolean(values[4]);

            createAccount(username,password,name,birthday);

        }

    }

    public void saveUserData() {
        ArrayList<String> userData = new ArrayList<>();
        accounts.forEach( (k,v) -> userData.add(v.toString()));

        io.saveData(userData, path, "username, password, name, birthday");


    }

    boolean isUserInSystem(String username) {
        return this.accounts.containsKey(username);
    }

    boolean isUserAdmin(){
    return false;
    }

}
