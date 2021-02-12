package movie.api.controllers;

import movie.api.models.Character;
import movie.api.models.Franchise;
import movie.api.models.Movie;
import movie.api.repositories.FranchiseRepository;
import movie.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static movie.api.controllers.ControllerHelper.*;

@RestController
@RequestMapping(value = BASE_URI_V1 + "/franchise")
public class FranchiseController {

    @Autowired
    private FranchiseRepository franchiseRepository;

    @GetMapping
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises, status);
    }

    @PostMapping
    public ResponseEntity<Franchise> createFranchise(@RequestBody Franchise newFranchise) {
        Franchise franchise = franchiseRepository.save(newFranchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(franchise, status);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable long id) {
        Franchise franchise = findFranchiseById(id);
        HttpStatus status;
        if (equalIds(franchise.getFranchiseId(), id)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(franchise, status);
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<List<Movie>> getAllFranchiseMovies(@PathVariable long id) {
        Franchise franchise = findFranchiseById(id);
        List<Movie> movies = null;
        HttpStatus status;
        if (equalIds(franchise.getFranchiseId(), id)) {
            movies = franchise.getMovies();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movies, status);
    }

    @GetMapping(value = "/{id}/characters")
    public ResponseEntity<List<Character>> getAllFranchiseCharacters(@PathVariable long id) {
        Franchise franchise = findFranchiseById(id);
        List<Character> characters = null;
        HttpStatus status;
        if (equalIds(franchise.getFranchiseId(), id)) {
            // TODO get all characters for franchise using franchiseRepository
            characters = new ArrayList<>();
            for (Movie movie : franchise.getMovies()) {
                characters.addAll(movie.getCharacters());
            }
            status = HttpStatus.I_AM_A_TEAPOT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(characters, status);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable long id, @RequestBody Franchise newFranchise) {
        Franchise franchise = new Franchise();
        HttpStatus status;
        if (notEqualIds(newFranchise.getFranchiseId(), id)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(franchise, status);
        }
        franchise = franchiseRepository.save(newFranchise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(franchise, status);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Franchise> deleteFranchise(@PathVariable long id) {
        HttpStatus status;
        if (franchiseExistsById(id)) {
            franchiseRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }

    private boolean franchiseExistsById(long id) {
        return franchiseRepository.existsById(id);
    }

    private Franchise findFranchiseById(long id) {
        if (franchiseExistsById(id)) {
            return franchiseRepository.findById(id).get();
        }
        return new Franchise();
    }

    @Autowired
    private MovieRepository movieRepository;

    @PutMapping(value = "/{franchiseId}/add/movie/{movieId}")
    public ResponseEntity<Object> addMovieToFranchise(@PathVariable long franchiseId, @PathVariable long movieId) {
        Franchise franchise = findFranchiseById(franchiseId);
        Movie movie = findMovieById(movieId);
        HttpStatus status;
        if (notEqualIds(franchise.getFranchiseId(), franchiseId)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(franchise, status);
        }
        if (notEqualIds(movie.getMovieId(), movieId)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(movie, status);
        }
        franchise.addMovie(movie);
        franchise = franchiseRepository.save(franchise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(franchise, status);
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
