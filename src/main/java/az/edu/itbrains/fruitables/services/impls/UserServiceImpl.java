package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.dtos.auth.RegisterDto;
import az.edu.itbrains.fruitables.models.Role;
import az.edu.itbrains.fruitables.models.User;
import az.edu.itbrains.fruitables.repositories.RoleRepository;
import az.edu.itbrains.fruitables.repositories.UserRepository;
import az.edu.itbrains.fruitables.services.EmailService;
import az.edu.itbrains.fruitables.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

    @Override
    public boolean register(RegisterDto registerDto) {

        if (userRepository.findByEmail(registerDto.getEmail()) != null) {
            return false;
        }

        User user = new User();

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setPassword(encodedPassword);
        user.setEmail(registerDto.getEmail());
        String activationCode = UUID.randomUUID().toString().replace("-", "");

        user.setConfirmationCode(activationCode);
        Date date = new Date();
        date.setTime(date.getTime() +  1000 * 60 * 60 * 24);
        user.setConfirmationExpiredCodeDate(date);
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        emailService.getConfirmationEmail(activationCode, registerDto);
        Role userRole = roleRepository.findAll().stream()
                .filter(r -> r.getName().equals("ROLE_USER"))
                .findFirst()
                .orElse(null);

        if (userRole != null) {
            user.setRoles(new ArrayList<>(List.of(userRole)));
        }
        userRepository.save(user);

        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean verifyUser(String token) {
        User findUser = userRepository.findByConfirmationCode(token);
        if (findUser != null && findUser.getConfirmationExpiredCodeDate().after(new Date())) {
            findUser.setConfirmationCode(null);
            findUser.setEnabled(true);
            findUser.setConfirmationExpiredCodeDate(null);
            findUser.setAccountNonExpired(true);
            findUser.setAccountNonLocked(true);
            findUser.setCredentialsNonExpired(true);
            userRepository.save(findUser);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
