package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.dtos.auth.RegisterDto;
import az.edu.itbrains.fruitables.models.User;

import java.util.List;

public interface UserService {

    boolean register(RegisterDto registerDto);
    User getUserByEmail(String email);

    boolean verifyUser(String token);

    List<User> getAllUsers();
    void deleteUser(Long id);
}
