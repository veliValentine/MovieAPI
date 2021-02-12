package movie.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<Genre> genres = new ArrayList<>();

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
            return "/api/v1/franchise/" + franchise.getFranchiseId();
        } else {
            return null;
        }
    }

    @ManyToMany(mappedBy = "movies")
    private List<Character> characters = new ArrayList<>();

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

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
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

    public void setMoviePictureSrc(String moviePictureSrc) {
        this.moviePictureSrc = moviePictureSrc;
    }

    public String getTrailerURI() {
        return trailerURI;
    }

    public void setTrailerURI(String trailerURI) {
        this.trailerURI = trailerURI;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
