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

        boolean isChoosing = true;

        while (isChoosing) {
            int choice = ui.promptInteger("1) Oprette en bruger" + "\n" + "2) Log ind med eksisterende bruger");

            if (choice == 1) {
                createAccount();
                start();
                isChoosing = false;

            } else if (choice == 2) {
                login();
                isChoosing = false;

                // else if (choice == 3) { closeApp(); }

            } else {
                ui.displayMessage("Vælg venligst en gyldig mulighed");
            }
        }

    }

    public void login() {
        boolean isPasswordCorrect = false;

        while(!isPasswordCorrect) {
            String userName = ui.promptText("Indtast dit brugernavn");
            String password = ui.promptText("Indtast password");
            if (manager.isUserNameAndPasswordCorrect(userName, password)) {
                //service.start();
                isPasswordCorrect = true;
            } else {
                ui.displayMessage("Brugernavn eller password forkert" + "\n" + "Prøv igen");
                System.out.println("");
            }
        }
    }


    public void createAccount() {
        String username = "";
        boolean isUserNameTaken = true;

                while(isUserNameTaken) {
                    username = ui.promptText("Indtast et ønsket brugernavn");
                    if (manager.isUserInSystem(username)) {
                        ui.displayMessage("Brugernavn er optaget");
                    }
                    else {
                        isUserNameTaken = false;
                    }
                }
                String password = ui.promptText("Lav et password");
                String name = ui.promptText("Indtast dit fornavn");
                LocalDate birthdate = ui.promptBirthday("Indtast fødselsoplysninger");
                manager.createAccount(username,password,name,birthdate);
    }
}