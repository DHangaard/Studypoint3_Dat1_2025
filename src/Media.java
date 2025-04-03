import java.util.List;

public abstract class Media {
    private String title;
    private List<String> genre;
    private double rating;

    public Media(String title, List<String> genre, double rating) {
        this.title = title;
        this.genre = genre;
        this. rating = rating;
    }
}
