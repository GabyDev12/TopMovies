package project.topmovies.logic;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    private String title;
    private List<String> actors;
    private List<String> categories;
    private List<String> director;
    private String posterURL;
    private String releaseDate;
    private String runtime;
    private String synopsis;
    private String trailerURL;
    private String year;


    public Movie(String title, List<String> actors, List<String> categories, List<String> director, String poster, String releaseDate, String runtime, String synopsis, String trailer, String year) {

        this.title = title;
        this.actors = actors;
        this.categories = categories;
        this.director = director;
        this.posterURL = poster;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.synopsis = synopsis;
        this.trailerURL = trailer;
        this.year = year;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return posterURL;
    }

    public void setPoster(String poster) {
        this.posterURL = poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTrailer() {
        return trailerURL;
    }

    public void setTrailer(String trailer) {
        this.trailerURL = trailer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
