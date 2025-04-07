import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.FileIO;
import util.TextUI;

public class MediaManager {
    FileIO io;
    private TextUI ui;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;
    private String moviePath;
    private String seriesPath;

    public MediaManager(String moviePath, String seriesPath){
        this.moviePath = moviePath;
        this.seriesPath = seriesPath;
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
            ArrayList<String> genre = new ArrayList<>(Arrays.asList(values[2].split(",")));
            double rating = Double.parseDouble(values[3].trim().replace(",", "."));

            Movie movie = new Movie(title,year,genre,rating);
            movies.add(movie);
        }
    }

    public void loadSeriesData(){
        //Get file data from file
        ArrayList<String> seriesData = io.readData(this.seriesPath);

        for(String s: seriesData){
            String[] values = s.split(";");
            String title = values[0].trim();
            String years = values[1].trim();
            String[] yearArray = years.split("-");
            int releaseYear = Integer.parseInt(yearArray[0].trim());

            // Revise start
            int endYear = (yearArray.length > 1 && !yearArray[1].trim().isEmpty())
                    ? Integer.parseInt(yearArray[1].trim())
                    : 2025; // TODO : Lav else statement til en flexibel dato
            // Revise end

            ArrayList<String> genre = new ArrayList<>(Arrays.asList(values[2].split(",")));
            double rating = Double.parseDouble(values[3].trim().replace(",", "."));
            ArrayList<String> episodesAndSeasons = new ArrayList<>();
            episodesAndSeasons.add(values[4]);
            Series serie = new Series(title,releaseYear, endYear,genre,rating,episodesAndSeasons);
            series.add(serie);
        }
    }

    public void saveMediaData(String title, int releaseYear, int endYear, ArrayList<String> genre, double rating, ArrayList<String> episodeAndSeasons){
        ArrayList<String> toSave = new ArrayList<>();
        loadSeriesData();
        ArrayList<Series> currentSave = getSeries();
        currentSave.add(new Series(title, releaseYear, endYear, genre , rating, episodeAndSeasons));
        for(Series s : currentSave){
            toSave.add(currentSave.toString());
        }
        io.saveData(toSave, seriesPath, "title; yearspan; genres; rating; seasons/episodes");
    }

    public void saveMediaData(String title, int year, List<String> genre, double rating){
        ArrayList<String> toSave = new ArrayList<>();
        loadMovieData();
        ArrayList<Movie> currentSave = getMovie();
        currentSave.add(new Movie(title, year, genre, rating));
        for(Movie m : currentSave){
            toSave.add(currentSave.toString());
        }
        io.saveData(toSave, moviePath, "title; year; genres; rating");
    }

    public ArrayList<Series> getSeries(){
        return this.series;
    }
    public ArrayList<Movie> getMovie(){
        return this.movies;
    }


}
