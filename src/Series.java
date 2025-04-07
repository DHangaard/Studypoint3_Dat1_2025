import java.util.ArrayList;

public class Series extends Media {
    private int releaseYear;
    private int endYear;
    private ArrayList<String> episodesAndSeasons;
    private ArrayList<Season> seasons;


    public Series(String title, int releaseYear, int endYear, ArrayList<String> genre, double rating){
        super(title, genre, rating);
        this.releaseYear = releaseYear;
        this.endYear = endYear;
        this.seasons = new ArrayList<>();
    }

    public void addSeason(Season season){
        this.seasons.add(season);
    }

    private void generateSeasons() {

        for(String s : this.episodesAndSeasons) {
            String[] values = s.split(",");

            for(int i = 0; i < values.length; i++) {
                String[] parts = values[i].split("-");

                int seasonNumber = Integer.parseInt(parts[0].trim());
                int episodes = Integer.parseInt(parts[1].trim());
                seasons.add(new Season(seasonNumber, episodes));
            }
        }
    }

    @Override
    public String toString() {
        return this.title + " , " + this.releaseYear + "-" + this.endYear + " , " + this.genre + " , " + this.rating + ", Seasons: " + this.seasons.size();
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
}
