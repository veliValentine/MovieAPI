package movie.api.controllers;

import movie.api.models.Character;
import movie.api.models.Movie;
import movie.api.repositories.CharacterRepository;
import movie.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static movie.api.controllers.ControllerHelper.BASE_URI_V1;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = BASE_URI_V1 + "/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    // get all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    // Create a new movie
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie newMovie) {
        Movie movie = movieRepository.save(newMovie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(movie, status);
    }

    // get movie by Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable long id) {
        Movie movie = new Movie();
        HttpStatus status;
        if (movieRepository.existsById(id)) {
            movie = movieRepository.findById(id).get();
            status = HttpStatus.OK;
        } else {
            // movie ids are not equal meaning findMovieById returned null Movie
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movie, status);
    }

    // get characters for a movie
    // returns a list of URIs to each character
    @GetMapping(value = "/{id}/characters")
    public ResponseEntity<List<Character>> getAllMovieCharactersByMovieId(@PathVariable long id) {
        List<Character> characters = new ArrayList<>();
        HttpStatus status;
        if (movieRepository.existsById(id)) {
            Movie movie = movieRepository.findById(id).get();
            characters = movie.getCharacters();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(characters, status);
    }

    // Modify movie by Id
    @PutMapping(value = "/{id}")
    public ResponseEntity<Movie> updateMovieById(@PathVariable long id, @RequestBody Movie newMovie) {
        Movie returnMovie = new Movie();
        HttpStatus status;
        if (movieRepository.existsById(id)) {
            Movie movie = movieRepository.findById(id).get();

            String updateMovieTitle = newMovie.getMovieTitle();
            String movieTitle = movie.getMovieTitle();
            if (!updateMovieTitle.equals(movieTitle)) {
                movie.setMovieTitle(updateMovieTitle);
            }

            String updateDirector = newMovie.getDirector();
            String director = movie.getDirector();
            if (!updateDirector.equals(director)) {
                movie.setDirector(updateDirector);
            }

            int updateReleaseYear = newMovie.getReleaseYear();
            int releaseYear = movie.getReleaseYear();
            if (updateReleaseYear != releaseYear) {
                movie.setReleaseYear(updateReleaseYear);
            }

            String updateGenres = newMovie.getGenres();
            String genres = movie.getGenres();
            if (!updateGenres.equals(genres)) {
                movie.setGenres(updateGenres);
            }

            String updateMoviePictureSrc = newMovie.getMoviePictureSrc();
            String moviePictureSrc = movie.getMoviePictureSrc();
            if (!updateMoviePictureSrc.equals(moviePictureSrc)) {
                movie.setMoviePictureSrc(updateMoviePictureSrc);
            }

            String updateTrailerURI = newMovie.getTrailerURI();
            String trailerURI = movie.getTrailerURI();
            if (!updateTrailerURI.equals(trailerURI)) {
                movie.setTrailerURI(updateTrailerURI);
            }

            returnMovie = movieRepository.save(movie);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnMovie, status);
    }

    // Delete movie by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable long id) {
        HttpStatus status;
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }

    @PutMapping(value = "/{movieId}/add/character/{characterId}")
    public ResponseEntity<Movie> addCharacterByIdToMovieById(@PathVariable long movieId, @PathVariable long characterId) {
        Movie movie = new Movie();
        HttpStatus status;
        boolean characterExists = characterRepository.existsById(characterId);
        boolean movieExists = movieRepository.existsById(movieId);
        if (characterExists) {
            if (movieExists) {
                movie = movieRepository.findById(movieId).get();
                boolean movieAssignedToFranchise = movie.getFranchise() != null;
                // The check below is necessary because during assignment of a character to a movie, it also has to be assigned to a franchise for the functionality of the app
                if (movieAssignedToFranchise) {
                    Character character = characterRepository.findById(characterId).get();
                    // The case when the character has already been assigned to the movie
                    if (movie.getCharacters().contains(character)) {
                        status = HttpStatus.NO_CONTENT;
                        return new ResponseEntity<>(movie, status);
                    } else {
                        character.setFranchise(movie.getFranchise());
                        movie.addCharacter(character);
                        movieRepository.save(movie);
                        characterRepository.save(character);
                        status = HttpStatus.OK;
                    }
                    // The movie is not assigned to a franchise
                } else {
                    status = HttpStatus.NOT_FOUND;
                }
            // The movie does not exist
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        // The character does not exist
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movie, status);
    }
}