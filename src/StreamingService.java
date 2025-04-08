import util.TextUI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StreamingService {

    // Attributes
    TextUI ui;
    private MediaManager manager;
    private Account currentUser;
    private ArrayList<String> choices;

    // Constructor

    public StreamingService(Account user){
        this.ui = new TextUI();
        this.manager = new MediaManager("data/movies.csv", "data/series.csv");
        this.currentUser = user;
        this.choices = new ArrayList<>(List.of("1. Search","2. Show seen media","3. Show saved media","4. Logout", "5. Exit to main menu"));
        this.manager.loadSeriesData();
        this.manager.loadMovieData();
    }

    // Methods
    public void showMenu(){
       String choice = ui.promptText("Søg efter en film/eller serie");
       ArrayList<Media> searchTitles = manager.searchMediaByTitle(choice);
       ui.displayMessage("Film som matcher titlen:");
       for(Media m: searchTitles){
           System.out.println(m);
       }

    }



    public void showSeenMedia(){
        ArrayList<String> mediaAsStrings = new ArrayList<>();
        for(int i = 0; i < currentUser.getSeenMedia().size(); i++){
            Media m = currentUser.getSeenMedia().get(i);
            mediaAsStrings.add((i + 1) + ". " + m);
        }
        ui.displayList(mediaAsStrings,"Sete film/serie");
    }


    public void showSavedMedia(){
        ArrayList<String> mediaAsStrings = new ArrayList<>();
        for(int i = 0; i < currentUser.getSavedMedia().size(); i++){
            Media m = currentUser.getSavedMedia().get(i);
            mediaAsStrings.add((i + 1) + ". " + m);
        }
        ui.displayList(mediaAsStrings,"Gemte film/serie");
    }

    public void chooseSeenMedia(){
        showSeenMedia();
        while(true) {
            int choice = ui.promptInteger("Vælg en film/serie vha. nummer");

            //Get the chosen movie/serie
            if (choice > 0 && choice <= currentUser.getSeenMedia().size()) {
                Media chosenMedia = currentUser.getSeenMedia().get(choice - 1);
                ui.displayMessage("Du har valgt " + chosenMedia);
                break;
            } else {
                ui.displayMessage("Ugyldig valg. Prøv igen");
            }
        }
    }

    public void chooseSavedMedia(){
        showSavedMedia();
        while(true) {
            int choice = ui.promptInteger("Vælg en film/serie vha. nummer");

            //Get the chosen movie/serie
            if (choice > 0 && choice <= currentUser.getSavedMedia().size()) {
                Media chosenMedia = currentUser.getSavedMedia().get(choice - 1);
                ui.displayMessage("Du har valgt " + chosenMedia);
                break;
            } else {
                ui.displayMessage("Ugyldig valg. Prøv igen");
            }
        }
    }


    public void logOut(){

    }


    public void endStreamingService(){

    }

    // Delete this

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
