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
@CrossOrigin(origins = "*")
@RequestMapping(value = BASE_URI_V1 + "/franchise")
public class FranchiseController {

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MovieRepository movieRepository;

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
    public ResponseEntity<Franchise> getFranchiseById(@PathVariable long id) {
        Franchise franchise = new Franchise();
        HttpStatus status;
        if (franchiseRepository.existsById(id)) {
            franchise = franchiseRepository.findById(id).get();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(franchise, status);
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<List<Movie>> getAllFranchiseMoviesByFranchiseId(@PathVariable long id) {
        List<Movie> movies = new ArrayList<>();
        HttpStatus status;
        if (franchiseRepository.existsById(id)) {
            Franchise franchise = franchiseRepository.findById(id).get();
            movies = franchise.getMovies();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movies, status);
    }

    @GetMapping(value = "/{id}/characters")
    public ResponseEntity<List<Character>> getAllFranchiseCharactersByFranchiseId(@PathVariable long id) {
        List<Character> characters = new ArrayList<>();
        HttpStatus status;
        if (franchiseRepository.existsById(id)) {
            Franchise franchise = franchiseRepository.findById(id).get();
            List<Movie> movies = franchise.getMovies();
            for(Movie movie: movies) {
                List<Character> movieCharacters = movie.getCharacters();
                for(Character character: movieCharacters) {
                    if(!characters.contains(character)) {
                        characters.add(character);
                    }
                }
            }
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(characters, status);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Franchise> updateFranchiseById(@PathVariable long id, @RequestBody Franchise newFranchise) {
        Franchise returnFranchise = new Franchise();
        HttpStatus status;
        if (franchiseRepository.existsById(id)) {
            returnFranchise = franchiseRepository.save(newFranchise);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnFranchise, status);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Franchise> deleteFranchiseById(@PathVariable long id) {
        HttpStatus status;
        if (franchiseRepository.existsById(id)) {
            franchiseRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }

    @PutMapping(value = "/{franchiseId}/add/movie/{movieId}")
    public ResponseEntity<Franchise> addMovieByIdToFranchiseById(@PathVariable long franchiseId, @PathVariable long movieId) {
        Franchise franchise = new Franchise();
        HttpStatus status;
        boolean movieAndFranchiseExist = movieRepository.existsById(movieId) && franchiseRepository.existsById(franchiseId);
        if (movieAndFranchiseExist) {
            franchise = franchiseRepository.findById(franchiseId).get();
            Movie movie = movieRepository.findById(movieId).get();
            movie.setFranchise(franchise);
            franchise.addMovie(movie);
            movie.setFranchise(franchise);
            franchiseRepository.save(franchise);
            movieRepository.save(movie);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(franchise, status);
    }
}
