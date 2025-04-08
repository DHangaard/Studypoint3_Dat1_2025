import java.util.ArrayList;
import java.util.Arrays;
import util.FileIO;
import util.TextUI;
import java.util.List;

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

            // Genre og rating
            ArrayList<String> genre = new ArrayList<>(Arrays.asList(values[2].split(",")));
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

    public void saveMediaData(String title, int releaseYear, int endYear, ArrayList<String> genre, double rating){
        ArrayList<String> toSave = new ArrayList<>();
        loadSeriesData();
        ArrayList<Series> currentSave = getSeries();
        currentSave.add(new Series(title, releaseYear, endYear, genre, rating));
        for(Series s : currentSave){
            toSave.add(currentSave.toString());
        }
        io.saveData(toSave, seriesPath, "title; yearspan; genres; rating; seasons/episodes");
    }

    public void saveMediaData(String title, int year, ArrayList<String> genre, double rating){
        ArrayList<String> toSave = new ArrayList<>();
        loadMovieData();
        ArrayList<Movie> currentSave = getMovie();
        currentSave.add(new Movie(title, year, genre, rating));
        for(Movie m : currentSave){
            toSave.add(currentSave.toString());
        }
        io.saveData(toSave, moviePath, "title; year; genres; rating");
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

    public ArrayList<Media> searchMediaByRating(int minimumRating){
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
            if(m.getGenre().contains(genre)){
                result.add(m);
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
