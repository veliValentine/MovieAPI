package movie.api.controllers;

import movie.api.models.Character;
import movie.api.models.Movie;
import movie.api.repositories.CharacterRepository;
import movie.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static movie.api.controllers.ControllerHelper.BASE_URI_V1;
import static movie.api.controllers.ControllerHelper.notEqualIds;
import static movie.api.controllers.ControllerHelper.equalIds;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = BASE_URI_V1 + "/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    // get all characters
    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters, status);
    }

    // post new character
    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character newCharacter) {
        Character character = characterRepository.save(newCharacter);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(character, status);
    }

    // get character by ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable long id) {
        HttpStatus status;
        Character character = findCharacterById(id);
        if (equalIds(id, character.getId())) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(character, status);
    }

    // put character by id
    @PutMapping(value = "/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable long id, @RequestBody Character newCharacter) {
        Character returnCharacter = new Character();
        HttpStatus status;
        // check that path id is the same as character id
        if (notEqualIds(id, newCharacter.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnCharacter, status);
        }
        returnCharacter = characterRepository.save(newCharacter);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnCharacter, status);
    }

    // delete character by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Character> removeCharacter(@PathVariable long id) {
        HttpStatus status;
        if (characterExists(id)) {
            characterRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }

    private boolean characterExists(long id) {
        return characterRepository.existsById(id);
    }

    private Character findCharacterById(long id) {
        if (characterExists(id)) {
            return characterRepository.findById(id).get();
        }
        return new Character();
    }

    @Autowired
    private MovieRepository movieRepository;

    @PutMapping(value = "/{characterId}/{movieId}")
    public ResponseEntity<Object> addMovieToCharacter(@PathVariable long characterId, @PathVariable long movieId) {
        Character character = findCharacterById(characterId);
        Movie movie = findMovieById(movieId);
        HttpStatus status;
        if (notEqualIds(character.getId(), characterId)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(character, status);
        }
        if (notEqualIds(movie.getMovieId(), movieId)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(movie, status);
        }
        character.addMovie(movie);
        character = characterRepository.save(character);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(character, status);
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
