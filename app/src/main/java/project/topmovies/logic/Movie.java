package project.topmovies.logic;

import java.util.Date;
import java.util.List;

public class Movie {

    private List<String> actors;
    private List<String> categories;
    private List<String> director;
    private String poster;
    private Date releaseDate;
    private int runtime;
    private String synopsis;
    private String trailer;
    private int year;


    public Movie(List<String> actors, List<String> categories, List<String> director, String poster, Date releaseDate, int runtime, String synopsis, String trailer, int year) {

        this.actors = actors;
        this.categories = categories;
        this.director = director;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.synopsis = synopsis;
        this.trailer = trailer;
        this.year = year;

    }


    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
