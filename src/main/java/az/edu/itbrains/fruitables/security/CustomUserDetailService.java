package az.edu.itbrains.fruitables.security;

import az.edu.itbrains.fruitables.models.User;
import az.edu.itbrains.fruitables.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User findUser = userRepository.findByEmail(username);
            if (findUser == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            org.springframework.security.core.userdetails.User loggedUser = new org.springframework.security.core.userdetails.User(
                    findUser.getEmail(),
                    findUser.getPassword(),
                    findUser.isEnabled(),
                    findUser.isAccountNonExpired(),
                    findUser.isCredentialsNonExpired(),
                    findUser.isAccountNonLocked(),
                    findUser.getAuthorities()
            );

            return loggedUser;
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
