package movie.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "franchises")
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "franchise_id")
    private long franchiseId;

    @Column(name = "franchise_name")
    private String franchiseName;

    @Column(name = "description")
    private String description;

    @OneToMany
    @JoinColumn(name = "franchise_id")
    private List<Movie> movies; // = new ArrayList<>();

    @JsonGetter("movies")
    public List<String> movieGetter() {
        if(movies != null) {
            return movies.stream()
                    .map(movie -> "/api/v1/movies" + movie.getMovieId())
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /*
    @OneToMany
    @JoinColumn(name = "franchise_id")
    private List<Character> characters = new ArrayList<>();
    */

    public Franchise() {
    }

    public Franchise(String name, String description) {
        this.franchiseName = name;
        this.description = description;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    /*
    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
     */

    public long getFranchiseId() {
        return franchiseId;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
