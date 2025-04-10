package managers;
import java.util.ArrayList;
import article.*;

import util.FileIO;
import util.TextUI;

public class MediaManager {
    FileIO io;
    private TextUI ui;
    private ArrayList<Media> mediaList;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;
    private String moviePath;
    private String seriesPath;

    public MediaManager(String moviePath, String seriesPath){
        this.moviePath = moviePath;
        this.seriesPath = seriesPath;
        this.mediaList = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.series = new ArrayList<>();
        this.io = new FileIO();
        this.ui = new TextUI();
    }

    public void loadMovieData(){
        //Get file data from file
        ArrayList<String> movieData = io.readData(this.moviePath);

        //Make movie objects and put in movies list
        for(String s: movieData){
            String[] values = s.split(";");
            String title = values[0].trim();
            int year = Integer.parseInt(values[1].trim());

            //Fjerner mellemrum i genre
            ArrayList<String> genre = new ArrayList<>();
            for (String g : values[2].split(",")) {
                genre.add(g.trim());
            }

            double rating = Double.parseDouble(values[3].trim().replace(",", "."));

            Movie movie = new Movie(title,year,genre,rating);
            this.mediaList.add(movie);
        }
    }

    public void loadSeriesData(){
        // Hent filens data
        ArrayList<String> seriesData = io.readData(this.seriesPath);

        for (String s : seriesData) {

            String[] values = s.split(";");
            String title = values[0].trim();

            // Håndter år
            String years = values[1].trim();
            String[] yearArray = years.split("-");
            int releaseYear = Integer.parseInt(yearArray[0].trim());
            int endYear = (yearArray.length > 1 && !yearArray[1].trim().isEmpty())
                    ? Integer.parseInt(yearArray[1].trim())
                    : 2025; // Default hvis ingen slutdato

            //Fjerner mellemrum i genre
            ArrayList<String> genre = new ArrayList<>();
            for (String g : values[2].split(",")) {
                genre.add(g.trim());
            }

            //Rating
            double rating = Double.parseDouble(values[3].trim().replace(",", "."));

            // Hent sæson- og episode
            ArrayList<Season> seasons = new ArrayList<>();
            for (int i = 4; i < values.length; i++) {
                // Del dataet op i hver sæson-episode par
                String[] seasonData = values[i].trim().split(",");
                for (String seasonInfo : seasonData) {
                    String[] seasonParts = seasonInfo.trim().split("-");
                    int seasonNumber = Integer.parseInt(seasonParts[0].replaceAll("[^0-9]", ""));
                    int episodeCount = Integer.parseInt(seasonParts[1].replaceAll("[^0-9]", ""));

                    // Opret Season objekt og tilføj til listen
                    seasons.add(new Season(seasonNumber, episodeCount));
                }
            }
            Series serie = new Series(title, releaseYear, endYear, genre, rating);
            for (Season season : seasons) {
                serie.addSeason(season);
            }
            this.mediaList.add(serie);
        }
    }

    /*
    // Add all the seasons and spisodes
    for (int i = 0; i < epsiodesAndSeason.size(); i++){
        seasonCSV += s;

        // Add comma except after last
        for (int j = ; j < (epsiodesAndSeason.size()-1); j++){
            seasonCSV += ", "
        }
    }

    // Add semicolon after last
    seasonCSV += ";";
     */


    public void saveMediaData(String title, int releaseYear, int endYear, ArrayList<String> genre, double rating, ArrayList<String> episodeAndSeason){

        StringBuilder genreCSVBuilder = new StringBuilder();
        for (int g = 0; g < genre.size(); g++) {
            genreCSVBuilder.append(genre.get(g));
            if (g < genre.size() - 1) {
                genreCSVBuilder.append(", ");
            }
        }
        String genreCSV = genreCSVBuilder.toString();

        StringBuilder seasonCSVBuilder = new StringBuilder();
        for (int g = 0; g < episodeAndSeason.size(); g++) {
            seasonCSVBuilder.append(episodeAndSeason.get(g));
            if (g < episodeAndSeason.size() - 1) {
                seasonCSVBuilder.append(", ");
            }
        }
        String seasonCSV = seasonCSVBuilder.toString();

        Series s = new Series(title, releaseYear, endYear, genre, rating);
        series.add(s);
        String seriesAndSeasonsCSV = title + "; " + releaseYear + "-" + endYear + "; " + genreCSV + "; " + rating + "; " + seasonCSV + ";";
        io.appendData(seriesAndSeasonsCSV, seriesPath);
    }

    public void saveMediaData(String title, int year, ArrayList<String> genre, double rating){
        Movie m = new Movie(title, year, genre, rating);

        StringBuilder genreCSVBuilder = new StringBuilder();
        for (int g = 0; g < genre.size(); g++) {
            genreCSVBuilder.append(genre.get(g));
            if (g < genre.size() - 1) {
                genreCSVBuilder.append(", ");
            }
        }
        String genreCSV = genreCSVBuilder.toString();
        movies.add(m);
        String movieCSV = title + "; " + year + "; " + genreCSV + "; " + rating;
        io.appendData(movieCSV, moviePath);
    }

    public ArrayList<Media> searchMediaByTitle(String title){
        ArrayList<Media> result = new ArrayList<>();
        for(Media m: this.mediaList){
            if(m.getTitle().toLowerCase().contains(title.toLowerCase())){
                result.add(m);
            }
        }
        return result;
    }

    public ArrayList<Media> searchMediaByRating(double minimumRating){
        ArrayList<Media> result = new ArrayList<>();
        for(Media m: this.mediaList){
            if(m.getRating()>=minimumRating){
                result.add(m);
            }
        }
        return result;
    }

    public ArrayList<Media> searchMediaByGenre(String genre){
        ArrayList<Media> result = new ArrayList<>();
        for(Media m: this.mediaList){
            for(String g: m.getGenre()) {
                if (g.equalsIgnoreCase(genre.trim())) {
                    result.add(m);
                }
            }
        }
        return result;
    }

    public ArrayList<Media> searchMediaByYear(int yearSearched){
        ArrayList<Media> result = new ArrayList<>();
        for(Media media: this.mediaList){
            if(media instanceof Movie){
                Movie movie = (Movie) media;
                if(movie.getYear() == yearSearched){
                    result.add(media);
                }
            }
            if(media instanceof Series){
                Series serie = (Series) media;
                if(serie.getReleaseYear() == yearSearched){
                    result.add(media);
                }
            }
        }
        return result;
    }

    public void removeMedia(String title) {
        searchMediaByTitle(title);

    }

    public ArrayList<Media> getMediaList(){
        return mediaList;
    }

    public ArrayList<Series> getSeries(){
        return this.series;
    }
    public ArrayList<Movie> getMovie(){
        return this.movies;
    }

    public void addMedia(Media media){
        this.mediaList.add(media);
    }

}
