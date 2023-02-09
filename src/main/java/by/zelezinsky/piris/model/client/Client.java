package by.zelezinsky.piris.model.client;

import by.zelezinsky.piris.model.account.BankAccount;
import by.zelezinsky.piris.model.deposit.Deposit;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
    private LocalDate birthday;

    @Column(nullable = false)
    private Boolean isMale;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "client")
    private Passport passport;

    @Column(nullable = false)
    private String birthPlace;

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

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "client")
    private List<Deposit> deposits;
}
