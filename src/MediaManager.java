import java.util.ArrayList;
import java.util.Arrays;
import util.FileIO;

public class MediaManager {
    FileIO io;
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

    public void saveMediaData(){

    }

    public ArrayList<Series> getSeries(){
        return this.series;
    }
    public ArrayList<Movie> getMovie(){
        return this.movies;
    }


}
