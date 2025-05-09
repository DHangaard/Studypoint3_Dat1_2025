import java.util.ArrayList;

public abstract class Media {
    protected String title;
    protected ArrayList<String> genre;
    protected double rating;

    public Media(String title, ArrayList<String> genre, double rating) {
        this.title = title;
        this.genre = genre;
        this. rating = rating;
    }

    public abstract String toStringcsv();

    public abstract String getTitle();

    public abstract ArrayList<String> getGenre();

    public abstract double getRating();

    public abstract void playMedia();

    public abstract void playMedia(Account user, int seasonNumber, int episodeNumber);

}
