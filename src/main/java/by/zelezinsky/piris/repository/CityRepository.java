package by.zelezinsky.piris.repository;

import by.zelezinsky.piris.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
}
