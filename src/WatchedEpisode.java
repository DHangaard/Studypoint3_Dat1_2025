import java.util.ArrayList;

public class WatchedEpisode extends Media {
    private Series series;
    private int seasonNumber;
    private int episodeNumber;

    public WatchedEpisode(String title, ArrayList<String> genre, double rating, Series series, int seasonNumber, int episodeNumber) {
        super(title, genre, rating);
        this.series = series;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String getTitle() {
        return series.getTitle();
    }

    @Override
    public ArrayList<String> getGenre() {
        return series.getGenre();
    }

    @Override
    public double getRating() {
        return series.getRating();
    }

    @Override
    public void playMedia() {

    }

    @Override
    public void playMedia(Account user, int seasonNumber, int episodeNumber) {

    }

    @Override
    public String toString() {
        return "Serie: " + series.getTitle() + ", Sæson: " + seasonNumber + ", Episode: " + episodeNumber;
    }
    @Override
    public String toStringcsv(){
        return null;
    }

    public Series getSeries() {
        return series;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }
}