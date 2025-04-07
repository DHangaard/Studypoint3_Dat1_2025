public class Season {
    private int episodes;
    private int season;

    public Season(int season, int episodes){
        this.season = season;
        this.episodes = episodes;
    }

    public int getSeasonNumber(){
        return season;
    }

    public int getNumberOfEpisodes(){
        return episodes;
    }

    public int getEpisodeCount() {
        return this.episodes;
    }
}
