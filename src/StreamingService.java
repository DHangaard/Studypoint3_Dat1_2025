import util.TextUI;

import java.util.ArrayList;
import java.util.List;

public class StreamingService {

    // Attributes
    TextUI ui;
    private MediaManager manager;
    private Account currentUser;
    private Login login;
    private ArrayList<String> choices;
    private boolean continueSearch;

    // Constructor

    public StreamingService(Account user){
        this.ui = new TextUI();
        this.manager = new MediaManager("data/movies.csv", "data/series.csv");
        this.login = new Login("metFlix");
        this.currentUser = user;
        this.choices = new ArrayList<>(List.of("1. Search","2. Show seen media","3. Show saved media","4. Logout", "5. Exit to main menu"));
        this.manager.loadSeriesData();
        this.manager.loadMovieData();
        this.continueSearch = false;
    }

    public void welcomeScreen(){
    ui.displayMessage("Velkommen tilbage " + currentUser.getName());
    showMenu();

    }

    public void showMenu(){

        ui.displayMessage("Hovedmenu:");
        int choice = ui.promptInteger("1) Søg" + "\n" + "2) Vis tidligere sete film og serier" + "\n" +
                "3) Vis gemte film og serier" + "\n" + "4) Log ud" + "\n" + "5) Afslut programmet" + "\n" + "6) saveSeries()");

        while(true){
            switch (choice) {
                case 1:
                    searchMenu();
                    return;
                case 2:
                    showSeenMedia();
                    return;
                case 3:
                    showSavedMedia();
                    return;
                case 4:
                    logOut();
                    return;
                case 5:
                    endProgram();
                    return; // not necessary - left in for aesthetics
                case 6:
                    saveSeries();
                    return;
                default:
                    choice = ui.promptInteger("Ugyldigt valgt vælg et tal mellem 1-5");

            }
        }
    }

    public void showSeenMedia(){
        ArrayList<Media> seenMedia = currentUser.getSeenMedia();
        if(!(seenMedia.isEmpty())) {
            ui.displayMessage("Liste over sete film og serier");
            for (int i = 0; i < seenMedia.size(); i++) {
                System.out.println(i + 1 + ") " + seenMedia.get(0));
            }
            boolean play = ui.promptBinary("Vil du afspille et af medierne, eller gå tilbage til hovedmenu? Y/N");
            if(play) {
                chooseMedia(seenMedia);
            }
            else {
                showMenu();
            }
        } else {
            ui.displayMessage("Ingen film eller serier, er tilføjet til sete film" + "\n" + "du vil blive sendt tilbage til hovedmenu");
            showMenu();
        }
    }

    public void showSavedMedia(){
        ArrayList<Media> savedMedia = currentUser.getSavedMedia();
        if(!(savedMedia.isEmpty())) {
            ui.displayMessage("Liste over gemte film og serier");
            for (int i = 0; i < savedMedia.size(); i++) {
                System.out.println(i + 1 + ") " + savedMedia.get(0));
            }
            boolean play = ui.promptBinary("Vil du afspille et af medierne, eller gå tilbage til hovedmenu? Y/N");
            if(play) {
                chooseMedia(savedMedia);
            }
            else {
                showMenu();
            }
        } else {
            ui.displayMessage("Ingen film eller serier, er tilføjet til gemte film" + "\n" + "du vil blive sendt tilbage til hovedmenu");
            showMenu();
        }
    }

    public void chooseMedia(ArrayList<Media> media){
        while(true) {
            int choice = ui.promptInteger("Vælg en film/serie vha. nummer");

            //Get the chosen movie/serie
            if (choice > 0 && choice <= media.size()) {
                Media chosenMedia = media.get(choice - 1);
                chosenMedia.playMedia();
                currentUser.addSeenMedia(chosenMedia);
                showMenu();
                break;
            } else {
                ui.displayMessage("Ugyldig valg. Prøv igen");
            }
        }
    }

    public void saveMovie() {
        //TODO : IF USER IS ADMIN!
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
        //TODO : IF USER IS ADMIN ONLY!
        ArrayList<String> genre = new ArrayList<>();
        ArrayList<String> episodeAndSeasons = new ArrayList<>();


        String title = ui.promptText("Skriv en titel");
        int releaseYear = ui.promptInteger("Skriv årstal serien er fra");
        int endYear = ui.promptInteger("Skriv årstal serien kører til (eller tryk enter)");

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

    public void searchMenu(){
        ui.displayMessage("Søg efter film eller serier:");
        int choice = 0;
        while(true) {
            choice = ui.promptInteger("1) Søg efter titel" + "\n" + "2) Søg efter årstal" + "\n" + "3) Søg efter genre" + "\n" + "4) Søg efter rating");
            if(choice == 1){
                String title = ui.promptText("Hvilken titel vil du søge efter?");
              searchTitle(title);
              break;
            } else if(choice == 2){
                int year = ui.promptInteger("Hvilet årstal vil du søge efter?");
                searchByYear(year);
                break;
            }else if (choice == 3){
                String genre = ui.promptText("Hvilen genre vil du søge efter?");
                searchCategory(genre);
                break;
            }else if (choice == 4){
                double rating = ui.promptDouble("hvilken rating vil du søge efter?");
                break;
            } else{
                ui.displayMessage("Vælg venligst et tal mellem 1-5");
            }
        }
    }

    private void searchTitle(String title){
        ArrayList<Media> searchTitles = manager.searchMediaByTitle(title);
        searchEngine(searchTitles,"titler");
        }



    private void searchCategory(String genre) {
        ArrayList<Media> genreList = manager.searchMediaByGenre(genre);
        searchEngine(genreList,"genre");
        }

        private void searchByYear ( int searchedYear){
            ArrayList<Media> yearList = manager.searchMediaByYear(searchedYear);
            searchEngine(yearList,"år");
            }


        private void searchByRating(double searchedRating) {
            ArrayList<Media> ratingList = manager.searchMediaByRating(searchedRating);
            searchEngine(ratingList, "rating");

        }
        private void searchNotFound() {
            ui.displayMessage("Ingen film / serie fundet med det søgekriterie");
            continueSearch = ui.promptBinary("Vil du søge igen?");
            if (continueSearch) {
                searchMenu();
            }
            showMenu();

        }

    public void handleMediaAction(int action, Media chosenMedia) {
        switch (action) {
            case 1:
                chosenMedia.playMedia();
                currentUser.addSeenMedia(chosenMedia);
                handlePostSearchAction(true);  // Mediet er afspillet
                break;
            case 2:
                currentUser.addSavedMedia(chosenMedia);
                handlePostSearchAction(false);  // Mediet er gemt til Se senere
                break;
            case 3:
                showMenu();  // Gå tilbage til hovedmenu
                break;
            default:
                ui.displayMessage("Ugyldigt valg. Prøv igen.");
                break;
        }
    }

    public void searchEngine(ArrayList<Media> media, String querry) {
        if (!(media.isEmpty())) {
            ui.displayMessage("Film og serier som matcher søgte" + querry + ":");
            for (int i = 0; i < media.size(); i++) {
                System.out.println(i + 1 + ") " + media.get(i));
            }

            boolean confirmSelection = ui.promptBinary("Vil du se en af disse titler? Y/N ");

            if (confirmSelection) {
                int mediaChoice = ui.promptInteger("Vælg venligst en af medierne ");
                Media chosen = media.get(mediaChoice - 1);
                int action = ui.promptInteger("1) Vil du afspille mediet?" + "\n" + "2) Gemme en film/ serie i Se senere" + "\n" + "3) Gå tilbage til hovedmenu");

                handleMediaAction(action, chosen);
            }
        } else {
            searchNotFound();
        }
    }

    public void handlePostSearchAction(boolean mediaPlayed) {
        if (mediaPlayed) {
            ui.displayMessage("Mediet er nu afspillet.");
        } else {
            ui.displayMessage("Mediet er gemt til Se senere.");
        }

        boolean continueSearch = ui.promptBinary("Vil du søge efter en anden film/serie? Y/N");
        if (continueSearch) {
            searchMenu();  // Sender brugeren tilbage til søgning
        } else {
            showMenu();  // Sender brugeren tilbage til hovedmenuen
        }
    }

    public void logOut(){
        ui.displayMessage("Du vil blive logget af, tak for denne gange");
        login.start();
    }

    public void endProgram(){
        // Save user state to CSV
        // manager.saveUserState(path)
        System.exit(0);
    }

}
