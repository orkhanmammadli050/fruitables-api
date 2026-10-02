package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
