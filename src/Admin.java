/*

*************************     DELETE THIS CLASS BEFORE PUBLISHING     *************************

 */


import java.time.LocalDate;
import java.util.ArrayList;

public class Admin extends Account{

    // Constructor
    public Admin(String username, String password, String name, LocalDate birthdate){
        super(username,password,name,birthdate);

    }

    // Methods

    protected void addMedia(Series series){

    }


    protected void addMedia(Movie movie){


    }


    public void removeMedia(String title) {

    }


    protected void changeMedia(String oldTitle, String newTitle){

    }


    protected void addAdmin(Account account){

    }
}
