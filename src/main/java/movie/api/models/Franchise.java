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
    private List<Movie> movies = new ArrayList<>();

    public Franchise() {
    }

    public Franchise(String name, String description) {
        this.franchiseName = name;
        this.description = description;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public long getId() {
        return franchiseId;
    }

    public String getName() {
        return franchiseName;
    }

    public void setName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
