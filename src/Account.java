import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

import static java.time.LocalDate.now;


public class Account {

    // Attributes
    private String name;
    private String username;
    private String password;
    private LocalDate birthday;
    private int age;
    private boolean isChild;
    private boolean isAdmin;
    private ArrayList<Media> seenMedia;
    private ArrayList<Media> savedMedia;

    // Constructor
    public Account(String username, String password, String name, LocalDate birthdate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthday = birthdate;
        this.isChild = true;
        this.isAdmin = false;
        this.seenMedia = new ArrayList<>();
        this.savedMedia = new ArrayList<>();
        calculateAge(birthdate);
        isChildBasedOnAge();

        //isAgeLessThan10(birthdate);

    }

    // Methods
public boolean makeThisAccountAdmin(String Password) {
        String adminPassword = "iAmAdmin-1234";
        if (password.equals(adminPassword)) {
            return true;
        }
        return false;
    }

    // Revise this - maybe use void instead of boolean in returnvalue
    private void makeAnotherAccountAdmin(HashMap<String, Account> accounts, String username){
        accounts.forEach((k, v) -> {
            if (k.equalsIgnoreCase(username)) {
                v.makeThisAccountAdmin("iAmAdmin-1234");
            }
        } );
    }


    private void isAgeLessThan10(LocalDate date){
       LocalDate convertYear = now().ofEpochDay(-3652);
       if(date.isBefore(convertYear)){
           this.isChild = false;
       }
    }

    private void calculateAge(LocalDate birthdate) {
        LocalDate currentDate = now();
        Period period = Period.between(birthdate, currentDate);
        this.age = period.getYears();
    }

    private void isChildBasedOnAge() {
        // Sætter isChild til true, hvis brugerens alder er under 10
        if (this.age < 10) {
            this.isChild = true;
        } else {
            this.isChild = false;
        }
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username =username;

    }

    // Review this method!
    public void setPassword(String oldPassword, String newPassword){
        if(oldPassword.equals(password)){
            password = newPassword;
        }else{
            System.out.println("wrong password !"); // TODO : skal vi have reccursion?
        }

    }
    public String getName(){return this.name;}

    public int getAge(){
        return this.age;
    }

    public String getPassword(){return this.password;}

    @Override
    public String toString(){
        String s = this.username + ", " + this.password + ", " + this.name + ", " + this.birthday + ", " + this.isAdmin;
        return s;
    }

    public void addSeenMedia(Media media){
        this.seenMedia.add(media);
    }

    public void addSavedMedia(Media media){
        this.savedMedia.add(media);
    }

    public ArrayList<Media> getSeenMedia(){
        return this.seenMedia;
    }

    public ArrayList<Media> getSavedMedia(){
        return this.savedMedia;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public boolean getAdmin(){
        return this.isAdmin;
    }

    public boolean isChild(){
        return this.isChild;
    }
}

