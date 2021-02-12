package movie.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private long movieId;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "director")
    private String director;

    @Column(name = "release_year")
    private int releaseYear;

    /*
     Should be ManyToMany
     models.Genre should have a list of movies
     */
    @OneToMany
    @JoinColumn(name = "genre_id")
    private List<Genre> genres;

    @JsonGetter("genres")
    public List<String> genreGetter() {
        if(genres != null) {
            return genres.stream()
                    .map(genre -> {
                        return "/api/v1/genres/" + genre.getGenreId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @Column(name = "movie_picture_src")
    private String moviePictureSrc;

    @Column(name = "trailer_uri")
    private String trailerURI;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @JsonGetter("franchise")
    public String franchise() {
        if(franchise != null) {
            return "/api/v1/franchise/" + franchise.getId();
        } else {
            return null;
        }
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    @ManyToMany(mappedBy = "movies")
    private List<Character> characters = new ArrayList<>();

    @JsonGetter("characters")
    public List<String> charactersGetter(){
        if(characters != null){
            return characters.stream()
                    .map(character -> {
                        return "/api/v1/characters/" + character.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    public Movie() {
    }

    public Movie(String movieTitle, String director, int releaseYear, List<Genre> genres, String moviePictureSrc, String trailerURI) {
        this.movieTitle = movieTitle;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.moviePictureSrc = moviePictureSrc;
        this.trailerURI = trailerURI;
    }

    // Getter and setters
    public long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getMoviePictureSrc() {
        return moviePictureSrc;
    }

    public void setMoviePictureSrc(String pictureSrc) {
        this.moviePictureSrc = pictureSrc;
    }

    public String getTrailerURI() {
        return trailerURI;
    }

    public void setTrailerURI(String trailerURI) {
        this.trailerURI = trailerURI;
    }
}
