package movie.api.controllers;

import movie.api.models.Movie;
import movie.api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Create new movie
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
        Movie movie = new Movie();
        if (movieRepository.existsById(id)) {
            movie = movieRepository.findById(id).get();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movie, status);
    }

    // Modify movie by Id
    @PutMapping(value = "/{id}")
    public ResponseEntity<Movie> modifyMovie(@PathVariable long id, @RequestBody Movie newMovie) {
        Movie returnMovie = new Movie();
        HttpStatus status;
        if (id != newMovie.getId()) {
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
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        }
        HttpStatus status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(null, status);
    }


    // get all characters in a movie
}
