package by.zelezinsky.piris.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private Boolean isMale;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "client")
    private Passport passport;

    @Column(nullable = false)
    private String birthdayPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private City livingCity;

    private String livingAddress;

    private String homePhoneNumber;

    private String mobilePhoneNumber;

    private String email;

    @JoinColumn(nullable = false)
    private FamilyStatus familyStatus;

    @JoinColumn(nullable = false)
    private String nationality;

    @JoinColumn(nullable = false)
    private DisabilityStatus disabilityStatus;

    @JoinColumn(nullable = false)
    private Boolean pensioner;

    private BigDecimal monthlyIncome;

    @JoinColumn(nullable = false)
    private Boolean isLiableMilitaryService;
}
