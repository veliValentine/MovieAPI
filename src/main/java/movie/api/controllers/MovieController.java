package movie.api.controllers;

import movie.api.models.Movie;
import movie.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static movie.api.controllers.ControllerHelper.notEqualIds;
import static movie.api.controllers.ControllerHelper.equalIds;

import java.util.List;

@RestController
@RequestMapping(value = ControllerHelper.BASE_URI_V1 + "/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    // get all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    // Create a new movie
    @PostMapping
    public ResponseEntity<Movie> createNewMovie(@RequestBody Movie newMovie) {
        Movie movie = movieRepository.save(newMovie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(movie, status);
    }

    // get movie by Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable long id) {
        HttpStatus status;
        Movie movie = findMovieById(id);
        if (equalIds(movie.getMovieId(), id)) {
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
    public ResponseEntity<List<String>> getMovieCharacters(@PathVariable long id) {
        HttpStatus status;
        List<String> characters = null;
        if (movieExists(id)) {
            characters = findMovieById(id).charactersGetter();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(characters, status);
    }

    // Modify movie by Id
    @PutMapping(value = "/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable long id, @RequestBody Movie newMovie) {
        Movie returnMovie = new Movie();
        HttpStatus status;
        if (notEqualIds(newMovie.getMovieId(), id)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnMovie, status);
        }
        returnMovie = movieRepository.save(newMovie);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnMovie, status);
    }

    // Delete movie by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable long id) {
        if (movieExists(id)) {
            movieRepository.deleteById(id);
        }
        // TODO add else statement for id not found
        HttpStatus status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(null, status);
    }

    private boolean movieExists(long id) {
        return movieRepository.existsById(id);
    }

    private Movie findMovieById(long id) {
        if (movieExists(id)) {
            return movieRepository.findById(id).get();
        }
        return new Movie();
    }
}