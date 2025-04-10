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
        this.login = new Login("Metflix");
        this.currentUser = user;
        this.choices = new ArrayList<>(List.of("1. Search","2. Show seen media","3. Show saved media","4. Logout", "5. Exit to main menu"));
        this.manager.loadSeriesData();
        this.manager.loadMovieData();
        this.continueSearch = false;
    }

    public void welcomeScreen(){
        if(currentUser.isChild()){
            ui.displayMessage("Metflix Kids");
        }
    ui.displayMessage("\uD83C\uDF89 Velkommen tilbage " + currentUser.getName() +" 🎉");
    showMenu();

    }

    public void showMenu(){
        if(!(currentUser.getAdmin())) {

            ui.displayMessage("\uD83D\uDCCB Hovedmenu:");
            int choice = ui.promptInteger("1)\uD83D\uDD0D Søg" + "\n" + "2)\uD83C\uDFA5 Vis tidligere sete film og serier" + "\n" +
                    "3)\uD83D\uDCBE Vis gemte film og serier" + "\n" + "4)\uD83D\uDEAA Log ud" + "\n" + "5)❌ Afslut programmet" + "\n" + "6) saveSeries()");

            while (true) {

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
                    default:
                        choice = ui.promptInteger("Ugyldigt valgt vælg et tal mellem 1-5");

                }
            }
        }
                ui.displayMessage("Hovedmenu:");
                int choiceAdmin = ui.promptInteger("1) Søg" + "\n" + "2) Vis tidligere sete film og serier" + "\n" +
                        "3) Vis gemte film og serier" + "\n" + "4) Log ud" + "\n" + "5) Afslut programmet" + "\n" +
                        "6) Tilføj ny film" + "\n" + "7) Tilføj ny serie" + "\n");

                while (true) {

                    switch (choiceAdmin) {

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
                            saveMovie();
                            showMenu();
                            return;
                        case 7:
                            saveSeries();
                            showMenu();
                            return;

                        default:
                            choiceAdmin = ui.promptInteger("Ugyldigt valgt vælg et tal mellem 1-7");

                    }
            }
        }


    public void showSeenMedia(){
        ArrayList<Media> seenMedia = currentUser.getSeenMedia();
        showMediaList(seenMedia, "sete film og serier");
    }

    public void showSavedMedia(){
        ArrayList<Media> savedMedia = currentUser.getSavedMedia();
        showMediaList(savedMedia, "gemte film og serie");
    }

    public void showMediaList(ArrayList<Media> mediaList, String mediaType) {
        if (!(mediaList.isEmpty())) {
            ui.displayMessage("Liste over " + mediaType + ":");
            for (int i = 0; i < mediaList.size(); i++) {
                ui.displayMessage(i + 1 + ") " + mediaList.get(i));
            }

            boolean play = ui.promptBinary("Vil du afspille et af medierne, eller gå tilbage til hovedmenu? Y/N");
            if (play) {
                chooseMedia(mediaList);
            } else {
                showMenu();
            }
        } else {
            ui.displayMessage("Ingen " + mediaType + " er tilføjet. Du vil blive sendt tilbage til hovedmenu.");
            showMenu();
        }
    }

    public void chooseMedia(ArrayList<Media> media){
        while(true) {
            int choice = ui.promptInteger("Vælg en film/serie vha. nummer");

            //Brugeren vælger et medie, afspiller og ligger i listen over sete medier
            if (choice > 0 && choice <= media.size()) {
                Media chosenMedia = media.get(choice - 1);
                playMediaAndSaveToList(chosenMedia);
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
            ui.displayList(manager.getGenreList(), "Mulige genrer at tilføje:");
            String genreToAdd = ui.promptText("Angiv genre");
            if (manager.getGenreList().contains(genreToAdd)){
                genre.add(genreToAdd);
            } else{
                ui.displayMessage("Genren kunne ikke tilføjes, prøv igen");
            }
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
        int releaseYear = ui.promptInteger("Skriv årstal (YYYY) serien er fra");
        int endYear = ui.promptInteger("Skriv årstal (YYYY) serien kører til eller skriv 0 hvis den ikke har slutår");

        //while for at få liste af genrer og sæsoner/episoder
        boolean addingGenres = true;
        while (addingGenres) {
            ui.displayList(manager.getGenreList(), "Mulige genrer at tilføje:");
            String genreToAdd = ui.promptText("Angiv genre");
            if (manager.getGenreList().contains(genreToAdd)){
                genre.add(genreToAdd);
        } else{
            ui.displayMessage("Genren kunne ikke tilføjes, prøv igen");
        }
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
            }
            else if(choice == 2){
                int year = ui.promptInteger("Hvilket årstal vil du søge efter?");
                searchByYear(year);
                break;
            }
            else if (choice == 3){
                String genre = ui.promptText("Hvilken genre vil du søge efter?");
                searchCategory(genre);
                break;
            }
            else if (choice == 4){
                double rating = ui.promptDouble("Hvilken minimum rating, vil du søge efter?");
                searchByRating(rating);
                break;
            }
            else{
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
            continueSearch = ui.promptBinary("Vil du søge igen? Y/N");
            if (continueSearch) {
                searchMenu();
            }
            showMenu();
        }

    // Play media
    public void playMediaAndSaveToList(Media chosenMedia){
        chosenMedia.playMedia();
        currentUser.addSeenMedia(chosenMedia);
        handlePostSearchAction(true);  // Mediet er afspillet
    }


    public void handleMediaAction(int action, Media chosenMedia) {

        while (true) {

            switch (action) {
                case 1:
                    playMediaAndSaveToList(chosenMedia);
                    return;
                case 2:
                    currentUser.addSavedMedia(chosenMedia);
                    handlePostSearchAction(false);  // Mediet er gemt til Se senere
                    return;
                case 3:
                    showMenu();  // Gå tilbage til hovedmenu
                    return;
                default:
                    ui.displayMessage("Ugyldigt valg. Prøv igen.");
                    action = ui.promptInteger("1) Vil du afspille mediet?" + "\n" +
                            "2) Gemme en film/ serie i Se senere" + "\n" +
                            "3) Gå tilbage til hovedmenu");
            }
        }
    }

    public void searchEngine(ArrayList<Media> media, String querry) {

        // Hvis brugeren er et barn, skal vi filtrere listen til kun at inkludere "Family"-genren
        if (currentUser.isChild()) {
            ArrayList<Media> filtered = new ArrayList<>();
            for (Media m : media) {
                if (m.getGenre().contains("Family")) { // Kun medier med "Family" genre
                    filtered.add(m);
                }
            }
            media = filtered; // Opdater listen med de filtrerede medier
        }

        //Hvis listen ikke er tom, vil fundne film/serier blive vist
        if (!(media.isEmpty())) {
            ui.displayMessage("Film og serier som matcher den søgte " + querry + ":");
            for (int i = 0; i < media.size(); i++) {
                ui.displayMessage(i + 1 + ") " + media.get(i));
            }
            ui.displayMessage("");

            boolean confirmSelection = ui.promptBinary("Vil du vælge en af titlerne? Y/N ");

            if (confirmSelection) {
                int mediaChoice = ui.promptInteger("Vælg venligst en af medierne ");

                if (mediaChoice > 0 && mediaChoice <= media.size()) {
                    Media chosen = media.get(mediaChoice - 1);
                    int action = ui.promptInteger("1) Vil du afspille mediet?" + "\n" + "2) Gemme en film/ serie i Se senere" + "\n" + "3) Gå tilbage til hovedmenu");
                    handleMediaAction(action, chosen);
                } else {
                    ui.displayMessage("Ugyldigt valg. Vælg et tal mellem 1 og " + media.size());
                    searchEngine(media, querry); // Giver brugeren en ny chance
                }
            } else {
                showMenu();
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

        boolean continueSearch = ui.promptBinary("Vil du søge efter en anden film/ serie? Y/N");
        if (continueSearch) {
            searchMenu();  // Sender brugeren tilbage til søgning
        } else {
            showMenu();  // Sender brugeren tilbage til hovedmenuen
        }
    }

    public void logOut(){
        ui.displayMessage("Du vil bliver nu logget af!");
        ui.displayMessage("Tak for denne gang, " + currentUser.getName() + "! Vi håber, du havde det sjovt! 🎉"+ "\n" + "Farvel og på gensyn! 👋");
        ui.displayMessage("");
        login.start();
    }

    public void endProgram(){
        // Save user state to CSV
        // manager.saveUserState(path)
        ui.displayMessage("Tak for denne gang, " + currentUser.getName() + "! Vi håber, du havde det sjovt! 🎉"+ "\n" + "Farvel og på gensyn! 👋");
        System.exit(0);
    }

}
