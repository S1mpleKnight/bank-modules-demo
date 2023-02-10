package by.zelezinsky.piris.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "imaginary_time")
public class ImaginaryTime {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "current_date_value")
    private LocalDate currentDate;
}
