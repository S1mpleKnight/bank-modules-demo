package by.zelezinsky.piris.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "passport")
public class Passport {

    @Id
    private UUID id = UUID.randomUUID();
    @OneToOne
    @JoinColumn(nullable = false, columnDefinition = "client_id")
    private Client client;
    @Column(nullable = false)
    private String passportSeries;
    @Column(nullable = false)
    private String passportNumber;
    @Column(nullable = false)
    private String issuingAuthority;
    @Column(nullable = false)
    private LocalDate issueDate;
    @Column(nullable = false)
    private String identityNumber;
}
