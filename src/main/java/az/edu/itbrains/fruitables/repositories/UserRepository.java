package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String username);

    User findByConfirmationCode(String token);
}
