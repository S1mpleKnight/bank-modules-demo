package by.zelezinsky.piris.model.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "bank_account")
@Builder
@AllArgsConstructor
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

    public BankAccount() {
    }
}
