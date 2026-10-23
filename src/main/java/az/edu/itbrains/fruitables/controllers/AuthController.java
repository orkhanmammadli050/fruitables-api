package az.edu.itbrains.fruitables.controllers;

import az.edu.itbrains.fruitables.dtos.auth.LoginDto;
import az.edu.itbrains.fruitables.dtos.auth.RegisterDto;
import az.edu.itbrains.fruitables.security.JwtUtil;
import az.edu.itbrains.fruitables.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        boolean success = userService.register(registerDto);

        Map<String, Object> body = new HashMap<>();
        if (!success) {
            body.put("success", false);
            body.put("message", "Bu email artıq qeydiyyatdan keçib");
            return ResponseEntity.badRequest().body(body);
        }

        body.put("success", true);
        body.put("message", "Qeydiyyat uğurludur");
        return ResponseEntity.ok(body);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            String token = jwtUtil.generateToken(loginDto.getEmail());

            Map<String, Object> body = new HashMap<>();
            body.put("success", true);
            body.put("token", token);
            return ResponseEntity.ok(body);

        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", "Email və ya şifrə yanlışdır");
            return ResponseEntity.status(401).body(body);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token) {
        boolean verified = userService.verifyUser(token);

        Map<String, Object> body = new HashMap<>();
        if (!verified) {
            body.put("success", false);
            body.put("message", "Təsdiqləmə linki yanlışdır və ya vaxtı bitib");
            return ResponseEntity.badRequest().body(body);
        }

        body.put("success", true);
        body.put("message", "Hesabınız uğurla təsdiqləndi");
        return ResponseEntity.ok(body);
    }
}