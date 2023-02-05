package by.zelezinsky.piris.repository;

import by.zelezinsky.piris.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
