package movie.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private long characterId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "alias")
    private String alias;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "character_picture_src")
    private String characterPictureSrc;

    @ManyToMany
    @JoinTable(
            name = "character_movie",
            joinColumns = {@JoinColumn(name = "character_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")}
    )
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



    public Character() {
    }

    public Character(String fullName, String alias, Gender gender, String characterPictureSrc) {
        this.fullName = fullName;
        this.alias = alias;
        this.gender = gender;
        this.characterPictureSrc = characterPictureSrc;
    }

    // Getter and setters
    public long getId() {
        return characterId;
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

    public String getCharacterPictureSrc() {
        return characterPictureSrc;
    }

    public void setCharacterPictureSrc(String pictureSrc) {
        this.characterPictureSrc = pictureSrc;
    }
}
