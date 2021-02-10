package movie.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "year")
    private int releaseYear;

    @Column(name = "genres")
    private String genres;

    @Column(name = "picture")
    private String pictureSrc;

    @Column(name = "trailer")
    private String trailerURI;


    //@ManyToMany(mappedBy = "")
    //private List<Character> characters;

    //@ManyToOne
    //@JoinColumn(name = "")
    private long franchiseId;

    /*
    @JsonGetter("")
    public List<String> charactersGetter(){
        if(characters != null){
            return characters.stream()
                    .map(character -> {
                        return "/api/v1/characters/" + character.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }
    */

    public Movie() {
    }

    public Movie(String title, String director, int releaseYear, String genres, String pictureSrc, String trailerURI) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.pictureSrc = pictureSrc;
        this.trailerURI = trailerURI;
    }

    // Getter and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPictureSrc() {
        return pictureSrc;
    }

    public void setPictureSrc(String pictureSrc) {
        this.pictureSrc = pictureSrc;
    }

    public String getTrailerURI() {
        return trailerURI;
    }

    public void setTrailerURI(String trailerURI) {
        this.trailerURI = trailerURI;
    }
}
