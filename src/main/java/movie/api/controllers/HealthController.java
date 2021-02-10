package movie.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ControllerHelper.BASE_URI_V1)
public class HealthController {

    @GetMapping(value = "/health")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
