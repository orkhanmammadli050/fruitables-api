package az.edu.itbrains.fruitables.controllers.admin;

import az.edu.itbrains.fruitables.dtos.user.UserDashboardDto;
import az.edu.itbrains.fruitables.models.User;
import az.edu.itbrains.fruitables.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<UserDashboardDto> users = userService.getAllUsers().stream()
                .map(this::toDashboardDto)
                .toList();

        Map<String, Object> body = new HashMap<>();
        body.put("users", users);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDashboardDto toDashboardDto(User user) {
        return UserDashboardDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .cashback(user.getCashback())
                .build();
    }
}