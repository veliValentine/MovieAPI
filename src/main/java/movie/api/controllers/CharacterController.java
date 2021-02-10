package movie.api.controllers;

import movie.api.models.Character;
import movie.api.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ControllerHelper.BASE_URI_V1 + "/characters")
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
        Character returnCharacter = new Character();
        HttpStatus status;
        if (characterRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnCharacter = characterRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnCharacter, status);
    }

    // put character by id
    @PutMapping(value = "/{id}")
    public ResponseEntity<Character> putCharacter(@PathVariable long id, @RequestBody Character newCharacter) {
        Character returnCharacter = new Character();
        HttpStatus status;
        // check that path id is the same as character id
        if (id != newCharacter.getId()) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnCharacter, status);
        }
        returnCharacter = characterRepository.save(newCharacter);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnCharacter, status);
    }

    // delete character by id
    // should be protected against malicious use. Muhahaha
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Character> removeCharacter(@PathVariable long id) {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
        }
        HttpStatus status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(null, status);
    }
}
