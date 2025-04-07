import util.TextUI;

import java.util.ArrayList;
import java.util.List;

public class StreamingService {

    // Attributes
    TextUI ui;
    private MediaManager manager;
    private Account account;
    private ArrayList<String> choices;
    // Constructor

    public StreamingService(){
        this.ui = new TextUI();
        this.manager = new MediaManager("data/movies.csv", "data/series.csv");
        this.account = account;
        this.choices = new ArrayList<>(List.of("1. Search","2. Show seen media","3. Show saved media","4. Logout", "5. Exit to main menu"));
        this.manager.loadSeriesData();
        this.manager.loadMovieData();
    }

    // Methods
    public void showMenu(){
        ArrayList<Series> series = manager.getSeries();

        for(Series s: series){
            System.out.println(s);
        }

        //ui.promptChoice(choices,1, "");

    }



    public void showSeenMedia(Account account){

    }


    public void showSavedMedia(Account account){

    }


    public void logOut(){

    }


    public void endStreamingService(){

    }


    private void displayMedia(){

    }


    // Search
    public void search(){

    }


    private void searchTitle(String title){

    }


    private void searchCategory(){

    }


    private void searchByYear(){

    }


    private void searchByRating(){

    }




}
