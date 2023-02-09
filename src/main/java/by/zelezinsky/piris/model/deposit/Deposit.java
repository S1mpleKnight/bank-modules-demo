package by.zelezinsky.piris.model.deposit;

import by.zelezinsky.piris.model.Currency;
import by.zelezinsky.piris.model.account.BankAccount;
import by.zelezinsky.piris.model.client.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "deposit")
public class Deposit {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private DepositType depositType;

    @Column(nullable = false)
    private Long contractNumber;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer contractTerm;

    @Column(nullable = false)
    private Integer percent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "client_id")
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_account_id", nullable = false)
    private BankAccount currentAccount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "percent_account_id", nullable = false)
    private BankAccount percentAccount;
}
