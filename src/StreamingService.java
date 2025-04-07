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

    public void saveMovie() {
        ArrayList<String> genre = new ArrayList<>();
        String title = ui.promptText("Skriv en titel");
        int year = ui.promptInteger("Skriv årstal filmen er fra");

        boolean addingGenres = true;
        while (addingGenres) {
            genre.add(ui.promptText("Angiv genre"));
            addingGenres = ui.promptBinary("Vil du tilføje en genre mere? (Y/N)");
        }

        double rating = ui.promptDouble("Giv et bedømmelsestal af filmen");

        manager.saveMediaData(title, year, genre, rating);
    }

    public void saveSeries() {
        ArrayList<String> genre = new ArrayList<>();
        ArrayList<String> episodeAndSeasons = new ArrayList<>();


        String title = ui.promptText("Skriv en titel");
        int releaseYear = ui.promptInteger("Skriv årstal serien er fra");
        int endYear = ui.promptInteger("Skriv årstal serien kører til (eller tryk enter");

        //while for at få liste af genrer og sæsoner/episoder
        boolean addingGenres = true;
        while (addingGenres) {
            genre.add(ui.promptText("Angiv genre"));
            addingGenres = ui.promptBinary("Vil du tilføje en genre mere? (Y/N)");
        }

        double rating = ui.promptDouble("Giv et bedømmelsestal af serien");

        boolean addingSeasons = true;
        while (addingSeasons) {
            episodeAndSeasons.add(ui.promptText("Angiv sæson og episoder i sæsonen i format 'sæsonnummer-episoder'"));
            addingSeasons = ui.promptBinary("Vil du tilføje en sæson mere? (Y/N)");
        }
        manager.saveMediaData(title, releaseYear, endYear, genre, rating, episodeAndSeasons);

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
