import java.time.LocalDate;
import static java.time.LocalDate.now;


public class Account {

    // Attributes
    private String name;
    private String username;
    private String password;
    private LocalDate birthday;
    private int age;
    private boolean isChild;

    // Constructor
    public Account(String username, String password, String name, LocalDate birthdate){
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthday = birthdate;
        this.isChild = true;
        isAgeLessThan10(birthdate);
    }

    // Methods
    private void isAgeLessThan10(LocalDate date){
       LocalDate convertYear = now().ofEpochDay(-3652);
       if(date.isBefore(convertYear)){
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
    public String getName(){
        return this.name;

    }
    public int getAge(){
        return this.age;
    }

    public String getPassword(){return this.password;}

    @Override
    public String toString(){
        String s = this.username + ", " + this.password + ", " + this.name + ", " + this.birthday;
        return s;
    }




}
