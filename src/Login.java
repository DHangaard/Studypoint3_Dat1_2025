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
                endProgram();

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
            username = ui.promptText("Indtast e-mail");
            //Hvis bruger giver tomt input
            if(username == null || username.trim().isEmpty()){
                ui.displayMessage("E-Mail kan ikke være tom.");
                continue;
            }
            // Email validering
            if (!username.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                ui.displayMessage("E-mail format er ugyldigt.");
                continue;
            }

            // Hvis brugernavnet er optaget
            if (manager.isUserInSystem(username)) {
                ui.displayMessage("Der er allerede oprettet en bruger med denne e-mail");

            } else {
                isUserNameTaken = false;
                    }
                }

        //Tjek om bruger opfylder password krav
        boolean validPassword = false;
        String password = "";

        while (!validPassword) {
            password = ui.promptText("Indtast et password:\nKrav: min. 6 tegn, min 1 stort bogstav, min 1. lille bogstav, min 1 tal:");
            validPassword = isValidPassword(password);
        }

        String name = "";
        boolean validName = false;
        //Tjek om bruger opfylder navne krav
        while (!validName){
            name = ui.promptText("Indtast dit navn: Minimum 2 tegn langt.");
            if(name.trim().length() < 2) {
                ui.displayMessage("Brugernavn skal mindst være 2 tegn langt.");
            }else{
                validName = true;
            }

        }
        //Tjek om bruger opfylder fødelsdags krav
        LocalDate birthdate = ui.promptBirthday("Indtast fødselsoplysninger");
        //Opret bruger i systemet
        manager.createAccount(username,password,name,birthdate);
    }

    public void endProgram(){
        // Save user state to CSV
        // manager.saveUserState(path)
        ui.displayMessage("Tak for denne gang! Vi håber, du havde det sjovt! 🎉"+ "\n" + "Farvel og på gensyn! 👋");
        System.exit(0);
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            ui.displayMessage("Password kan ikke være tomt.");
            return false;
        }
        if (password.length() < 6) {
            ui.displayMessage("Password skal være mindst 6 tegn langt.");
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            ui.displayMessage("Password skal indeholde mindst et stort bogstav.");
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            ui.displayMessage("Password skal indeholde mindst et lille bogstav.");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            ui.displayMessage("Password skal indeholde mindst et tal.");
            return false;
        }
        return true;
    }

}