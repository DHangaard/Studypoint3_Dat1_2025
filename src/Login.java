import util.TextUI;

import java.time.LocalDate;

public class Login {
    // Attributes
    static TextUI ui;
    private AccountManager manager;
    private StreamingService streamingService;
    private String appName;

    // Constructor
    public Login(String appName) {
        this.ui = new TextUI();
        this.manager = new AccountManager();
        this.appName = appName;
    }

    // Methods
    public void start() {

        ui.displayMessage("Velkommen til " + this.appName + "\n" + "Du har følgende valgmuligheder:");

        boolean isChoosen = true;

        while (isChoosen) {
            int choice = ui.promptInteger("1) Oprette en bruger" + "\n" + "2) Log ind med eksisterende bruger" + "\n" + "3) Afslut programmet");

            if (choice == 1) {
                createAccount();
                start();
                isChoosen = false;

            } else if (choice == 2) {
                login();
                isChoosen = false;

            } else if (choice == 3) {
                streamingService.endProgram();

            } else {
                ui.displayMessage("Vælg venligst en gyldig mulighed");
            }
        }
    }

    public void login() {
        boolean isPasswordCorrect = false;

        while(!isPasswordCorrect) {
            String userName = ui.promptText("Indtast dit brugernavn: ");
            String password = ui.promptText("Indtast password: ");
            if (manager.isUserNameAndPasswordCorrect(userName, password)) {
                Account user = manager.getAccount(userName);
                streamingService = new StreamingService(user);
                streamingService.welcomeScreen();
                isPasswordCorrect = true;
            } else {
                ui.displayMessage("Brugernavn eller password forkert" + "\n" + "Prøv igen");
                System.out.println("");
            }
        }
    }

    public String createPassword(){
        String password = "";
        String confirmPassword;
        boolean isPasswordNotIdentical = true;

        while (isPasswordNotIdentical) {
            password = ui.promptText("Indtast password: ");
            confirmPassword = ui.promptText("Indtast password igen: ");

            if (confirmPassword.equals(password)) {
                isPasswordNotIdentical = false;
            } else {
                ui.displayMessage("De to passwords er ikke identiske. Prøv igen: ");
            }
        }
        return password;
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
                String password = createPassword();
                String name = ui.promptText("Indtast dit fornavn");
                LocalDate birthdate = ui.promptBirthday("Indtast fødselsoplysninger");
                manager.createAccount(username,password,name,birthdate);
    }
}