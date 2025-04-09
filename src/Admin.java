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

    protected void addSeries(Series series){
        // add a new series
    }

    protected void addSeason(Series series, int seasons){
        // add a number of empty seasons to a series
    }

    protected void addEpisode(Series series, int season, int episodes){
        // add a number of episodes to a season
    }

    protected void addMovie(Movie movie){
        // add a new movie
    }


    public void removeMedia(String title) {
        // Remove media, either tile series
    }


    protected void changeMediaTitle(String oldTitle, String newTitle){
    }

    protected void changeMediaRating(String oldTitle, String newTitle){
    }

    protected void addAdmin(Account account){

    }
}
