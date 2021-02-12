package movie.api.controllers;

import movie.api.models.Franchise;
import movie.api.repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static movie.api.controllers.ControllerHelper.BASE_URI_V1;
import static movie.api.controllers.ControllerHelper.equalIds;

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

    private Franchise findFranchiseById(long id) {
        if (franchiseRepository.existsById(id)) {
            return franchiseRepository.findById(id).get();
        }
        return new Franchise();
    }
}
