package by.zelezinsky.piris.model.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "city")
public class City {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;
}
