import java.util.ArrayList;
import java.util.List;

public class Movie extends Media {

    //Atributes
    private int year;

    //Constructor
    public Movie(String title, int year, List<String> genre, double rating) {
        super(title, genre, rating);
        this.year = year;



    }

    //Methods
    @Override
    public String toString(){
        String s = title + " " + year + " " + genre + " " + " " + rating;
        return s;
    }
    public String getTitle(){
        return this.title;
    }

    public int getYear(){
        return this.year;
    }
    public List<String> getGenre(){
        return this.genre;

    }
    public double getRating(){
        return this.rating;
    }



    //@Override
    public void playMedia(){

    }

    //@Override
    public void stopMedia(){

    }

}
