import java.util.ArrayList;

public class Movie extends Media {

    //Atributes
    private int year;

    //Constructor
    public Movie(String title, int year, ArrayList<String> genre, double rating) {
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

    public ArrayList<String> getGenre(){return this.genre;}

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
