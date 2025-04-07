import java.time.LocalDate;

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


    protected void removeMedia(String title){

    }


    protected void changeMedia(String oldTitle, String newTitle){

    }


    protected void addAdmin(Account account){

    }
}
