package movie.api.controllers;

import movie.api.models.Genre;
import movie.api.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static movie.api.controllers.ControllerHelper.*;

@RestController
@RequestMapping(value = BASE_URI_V1 + "/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(genres, status);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre newGenre) {
        Genre genre = genreRepository.save(newGenre);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(genre, status);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable long id) {
        Genre genre = findGenreById(id);
        HttpStatus status;
        if (equalIds(genre.getGenreId(), id)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(genre, status);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable long id, @RequestBody Genre newGenre) {
        Genre genre = new Genre();
        HttpStatus status;
        if (notEqualIds(newGenre.getGenreId(), id)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(genre, status);
        }
        genre = genreRepository.save(newGenre);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(genre, status);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Genre> deleteGenre(@PathVariable long id) {
        HttpStatus status;
        if (genreExists(id)) {
            genreRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }

    private boolean genreExists(long id) {
        return genreRepository.existsById(id);
    }


    private Genre findGenreById(long id) {
        if (genreExists(id)) {
            return genreRepository.findById(id).get();
        }
        return new Genre();
    }
}
