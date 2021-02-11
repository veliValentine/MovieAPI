package movie.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
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
    private List<Movie> movies;

    @JsonGetter("movies")
    public List<String> moviesGetter() {
        if(movies != null){
            return movies.stream()
                    .map(movie -> {
                        return "/api/v1/movies/" + movie.getMovieId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    public Franchise() {
    }

    public Franchise(String name, String description) {
        this.franchiseName = name;
        this.description = description;
    }

    public long getId() {
        return franchiseId;
    }

    public void setId(long id) {
        franchiseId = id;
    }

    public String getName() {
        return franchiseName;
    }

    public void setName(String name) {
        franchiseName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
