package by.zelezinsky.piris.repository;

import by.zelezinsky.piris.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassportRepository extends JpaRepository<Passport, UUID> {
}
