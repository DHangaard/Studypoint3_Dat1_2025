import util.TextUI;

import java.util.ArrayList;

public class Series extends Media {
    private int releaseYear;
    private int endYear;
    TextUI ui;
    private ArrayList<String> episodesAndSeasons;
    private ArrayList<Season> seasons;


    public Series(String title, int releaseYear, int endYear, ArrayList<String> genre, double rating){
        super(title, genre, rating);
        this.releaseYear = releaseYear;
        this.endYear = endYear;
        this.seasons = new ArrayList<>();
        this.ui = new TextUI();
    }

    public void chooseMediaAndEpisode(Account user){
        if(this.seasons.isEmpty()){
            ui.displayMessage("Ingen sæsoner tilgængelige");
            return;
        }

        ui.displayMessage("Antal sæsoner og episoder:");
        for(int i = 0; i < this.seasons.size(); i++){
            Season season = this.seasons.get(i);
            ui.displayMessage(i + 1 + ") " + this.title + ", Sæson: " + season.getSeasonNumber() + ", Antal episoder: " + season.getEpisodeCount());
        }
        int seasonChoice = 0;
        while (true) {
            seasonChoice= ui.promptInteger("Indtast sæson nummer for at vælge:");

            if (seasonChoice >= 1 && seasonChoice <= this.seasons.size()) {
                break;
            } else {
                ui.displayMessage("Ugyldigt valg af sæson, prøv igen");
            }
        }
        Season selectedSeason = this.seasons.get(seasonChoice-1);

        int episodeChoice = 0;

        while(true) {
            episodeChoice = ui.promptInteger("Indtast episodenummer for at vælge: ");

            if (episodeChoice >= 1 && episodeChoice <= selectedSeason.getNumberOfEpisodes()) {
                break;
            }
            else {
                ui.displayMessage("Ugyldigt valg af episode, prøv igen");
            }
        }

        // Gem episoden som set
        WatchedEpisode watchedEpisode = new WatchedEpisode(this.title,this.genre,this.rating,this, selectedSeason.getSeasonNumber(), episodeChoice);
        //Afspil serien
        this.playMedia(user, selectedSeason.getSeasonNumber(), episodeChoice);
        user.addSeenMedia(watchedEpisode);
    }

    public void addSeason(Season season){
        this.seasons.add(season);
    }

    @Override
    public String toString() {
        return "Title: " + this.title + ", " + "Årstal: " + this.releaseYear + "-" + this.endYear + ", " + "Genre: " +  this.genre + ", " + "Rating: " + this.rating + ", Sæsoner: " + this.seasons.size();
    }

    public int getReleaseYear(){
        return this.releaseYear;
    }

    public int getEndYear(){
        return this.endYear;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public ArrayList<String> getGenre() {
        return this.genre;
    }

    @Override
    public double getRating() {
        return this.rating;
    }

    public void playMedia(){
        System.out.println("Afspiller nu: \"" + this.title + "\" \uD83C\uDFAC");
    }

    @Override
    public void playMedia(Account user, int seasonNumber, int episodeNumber) {
        System.out.println("Afspiller nu Sæson " + seasonNumber + ", Episode " + episodeNumber + " af serien: " + this.title + "\" \uD83C\uDFAC");
    }
}
