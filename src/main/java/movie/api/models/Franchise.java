package movie.api.models;

import javax.persistence.*;
import java.util.List;

//@Entity
//@Table(name = "")
public class Franchise {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@Column(name = )
    private String name;

    //@Column(name = )
    private String description;

    //@OneToMany
    //@JoinColumn(name = "")
    private List<Movie> movies;

    public Franchise() {
    }

    public Franchise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
