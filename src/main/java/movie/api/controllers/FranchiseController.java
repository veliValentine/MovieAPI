package movie.api.controllers;

import movie.api.models.Franchise;
import movie.api.repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (equalIds(franchise.getId(), id)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(franchise, status);
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<List<String>> getAllFranchiseMovies(@PathVariable long id) {
        Franchise franchise = findFranchiseById(id);
        List<String> movies = null;
        HttpStatus status;
        if (equalIds(franchise.getId(), id)) {
            movies = franchise.moviesGetter();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movies, status);
    }

    @GetMapping(value = "/{id}/characters")
    public ResponseEntity<List<String>> getAllFranchiseCharacters(@PathVariable long id) {
        Franchise franchise = findFranchiseById(id);
        List<String> characters = null;
        HttpStatus status;
        if (equalIds(franchise.getId(), id)) {
            // TODO get all characters for franchise using franchiseRepository
            characters = franchise.moviesGetter();
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
        if (notEqualIds(newFranchise.getId(), id)) {
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
}
