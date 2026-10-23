package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
}
