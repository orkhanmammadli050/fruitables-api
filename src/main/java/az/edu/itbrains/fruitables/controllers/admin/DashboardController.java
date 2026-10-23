package az.edu.itbrains.fruitables.controllers.admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public ResponseEntity<Void> dashboard(){
        return ResponseEntity.ok().build();
    }
}
