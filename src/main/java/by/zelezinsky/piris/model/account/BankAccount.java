package by.zelezinsky.piris.model.account;

import by.zelezinsky.piris.model.client.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "bank_account")
public class BankAccount {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private AccountCodeType codeType;

    @Column(nullable = false)
    private AccountActivityType activityType;

    @Column(nullable = false)
    private BigDecimal debit;

    @Column(nullable = false)
    private BigDecimal credit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
