import java.lang.reflect.Array;
import java.util.ArrayList;

public class Series extends Media {
    private int releaseYear;
    private int endYear;
    private ArrayList<String> episodesAndSeasons;
    private ArrayList<Season> seasons;


    public Series(String title, int releaseYear, int endYear, ArrayList<String> genre, double rating, ArrayList<String> episodeAndSeasons){
        super(title, genre, rating);
        this.releaseYear = releaseYear;
        this.endYear = endYear;

        this.episodesAndSeasons = new ArrayList<>();
        this.episodesAndSeasons = episodeAndSeasons;

        this.seasons = new ArrayList<>();
        generateSeasons();
    }

    private void generateSeasons() {

        for(String s : this.episodesAndSeasons) {
            String[] values = s.split("-");
            int seasonNumber = Integer.parseInt(values[0]);
            int episodes = Integer.parseInt(values[1]);
            seasons.add(new Season(seasonNumber, episodes));
        }
    }



}
