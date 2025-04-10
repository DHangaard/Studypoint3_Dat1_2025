import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

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

    }

public boolean makeThisAccountAdmin(String Password) {
    String adminPassword = "iAmAdmin-1234";
    if (password.equals(adminPassword)) {
        return true;
    }
    return false;
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

    public boolean getIsAdmin(){
        return this.isAdmin;
    }

    public boolean getIsChild(){
        return this.isChild;
    }
}

