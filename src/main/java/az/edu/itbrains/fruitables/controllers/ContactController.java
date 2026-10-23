package az.edu.itbrains.fruitables.controllers;

import az.edu.itbrains.fruitables.dtos.contact.ContactDto;
import az.edu.itbrains.fruitables.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ContactController {
    private final EmailService emailService;

    @PostMapping("/contact")
    public ResponseEntity<Map<String, Object>> sendMessage(@Valid @RequestBody ContactDto contactDto) {
        boolean sent = emailService.sendContactMessage(
                contactDto.getName(), contactDto.getEmail(), contactDto.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("success", sent);
        return ResponseEntity.ok(body);
    }
}