import java.util.ArrayList;

public class Season {
    private int episodes;
    private int season;

    public Season(int season, int episodes){
        this.season = season;
        this.episodes = episodes;
    }

    public int getSeason(){
        return season;
    }

    public int getEpisodes(){
        return episodes;
    }

}
