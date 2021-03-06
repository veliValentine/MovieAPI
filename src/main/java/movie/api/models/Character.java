package movie.api.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.ArrayList;
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
            name = "movie_character",
            joinColumns = {@JoinColumn(name = "character_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")}
    )
    private List<Movie> movies; // = new ArrayList<>();

    @JsonGetter("movies")
    public List<String> movieGetter() {
        if(movies != null) {
            return movies.stream()
                    .map(movie -> "/api/v1/movies/" + movie.getMovieId())
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @JsonGetter("franchise")
    public String franchiseGetter() {
        if(franchise != null) {
            return "/api/v1/franchises/" + franchise.getFranchiseId();
        } else {
            return null;
        }
    }

    public Character() {
    }

    public Character(String fullName, String alias, Gender gender, String characterPictureSrc) {
        this.fullName = fullName;
        this.alias = alias;
        this.gender = gender;
        this.characterPictureSrc = characterPictureSrc;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    // Getter and setters

    public long getCharacterId() {
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

    public void setCharacterPictureSrc(String characterPictureSrc) {
        this.characterPictureSrc = characterPictureSrc;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Long) this.characterId).hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Character other = (Character) obj;
        if (this.characterId == 0) {
            return other.characterId == 0;
        } else return this.characterId == other.characterId;
    }
}
