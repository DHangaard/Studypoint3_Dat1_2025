import java.util.List;

public abstract class Media {
    protected String title;
    protected List<String> genre;
    protected double rating;

    public Media(String title, List<String> genre, double rating) {
        this.title = title;
        this.genre = genre;
        this. rating = rating;
    }
}
