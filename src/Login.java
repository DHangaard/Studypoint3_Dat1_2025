import util.TextUI;

import java.time.LocalDate;

public class Login {
    // Attributes
    static TextUI ui;
    private AccountManager manager;
    private String appName;

    // Constructor
    public Login(String appName) {
        this.appName = appName;
        this.manager = new AccountManager();
        this.ui = new TextUI();
    }

    // Methods
    public void start() {

        ui.displayMessage("Welcome to " + this.appName + "\n" + "Du har følgende valgmuligheder:");
        login();
    }

    public void login() {
        boolean isChoosing = true;

        while (isChoosing) {
            int choice = ui.promptInteger("1) Oprette en bruger" + "\n" + "2) Log ind med eksisterende bruger");

            if (choice == 1) {
                createAccount();
                isChoosing = false;

            } else if (choice == 2) {
                //manager.login();
                isChoosing = false;

            } else {
                ui.displayMessage("Vælg venligst en gyldig mulighed");
            }
        }
    }


    public void createAccount() {
        String username = "";
                while(true) {
                    username = ui.promptText("Indstast et ønsket brugernavn");
                    if (manager.isUserInSystem(username)) {
                        ui.displayMessage("Sorry username already taken");

                    }
                    break;
                }

                String password = ui.promptText("Venligst set et password");
                String name = ui.promptText("Indtast dit fornavn");
                LocalDate birthdate = ui.promptBirthday("Indtast fødselsoplysninger");
                manager.createAccount(username,password,name,birthdate);

    }
}