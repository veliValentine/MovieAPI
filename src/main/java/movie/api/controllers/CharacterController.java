package movie.api.controllers;

import movie.api.models.Character;
import movie.api.repositories.CharacterRepository;
import movie.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static movie.api.controllers.ControllerHelper.BASE_URI_V1;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = BASE_URI_V1 + "/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    // get all characters
    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters, status);
    }

    // post new character
    @PostMapping
    public ResponseEntity<Character> createCharacter(@RequestBody Character newCharacter) {
        Character character = characterRepository.save(newCharacter);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(character, status);
    }

    // get character by ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable long id) {
        Character returnCharacter = new Character();
        HttpStatus status;
        if (characterRepository.existsById(id)) {
            returnCharacter = characterRepository.findById(id).get();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnCharacter, status);
    }

    // put character by id
    @PutMapping(value = "/{id}")
    public ResponseEntity<Character> updateCharacterById(@PathVariable long id, @RequestBody Character newCharacter) {
        Character returnCharacter = new Character();
        HttpStatus status;
        if (characterRepository.existsById(id)) {
            returnCharacter = characterRepository.save(newCharacter);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnCharacter, status);
    }

    // delete character by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Character> deleteCharacterById(@PathVariable long id) {
        HttpStatus status;
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }
}
