package movie.api.models;

import javax.persistence.*;
import java.util.List;

//@Entity
//@Table(name = "ENTER_TABLE_NAME_WHEN_SCHEMA_IS_UP_AND_RUNNING")
public class Character {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@Column(name = )
    private String fullName;

    //@Column(name = )
    private String alias;

    //@Column(name = )
    private Gender gender;

    //@Column(name = )
    private String pictureSrc;

    //@ManyToMany(mappedBy = "")
    private List<Movie> movies;
    /*
    @JsonGetter("")
    public List<String> moviesGetter() {
        if(movies != null){
            return movies.stream()
                    .map(movie -> {
                        return "/api/v1/movies/" + movie.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }
    */


    public Character() {
    }

    public Character(String fullName, String alias, Gender gender, String pictureSrc) {
        this.fullName = fullName;
        this.alias = alias;
        this.gender = gender;
        this.pictureSrc = pictureSrc;
    }

    // Getter and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPictureSrc() {
        return pictureSrc;
    }

    public void setPictureSrc(String pictureSrc) {
        this.pictureSrc = pictureSrc;
    }
}
