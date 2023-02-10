package by.zelezinsky.piris.dto.account;

import by.zelezinsky.piris.model.account.AccountActivityType;
import by.zelezinsky.piris.model.account.AccountCodeType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BankAccountDto {

    private UUID id;
    private String number;
    private AccountCodeType codeType;
    private AccountActivityType activityType;
    private BigDecimal debit;
    private BigDecimal credit;
    private AccountCodeType accountCodeType;
    private String clientData;
}
