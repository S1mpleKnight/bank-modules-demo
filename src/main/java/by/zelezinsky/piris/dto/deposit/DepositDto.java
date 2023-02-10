package by.zelezinsky.piris.dto.deposit;

import by.zelezinsky.piris.dto.account.BankAccountDto;
import by.zelezinsky.piris.dto.client.ClientDto;
import by.zelezinsky.piris.model.Currency;
import by.zelezinsky.piris.model.deposit.DepositType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class DepositDto {

    private static final String DATE_REGEXP = "yyyy-MM-dd";
    private static final String NUMBER_REGEXP = "[0-9][1-9]";
    private static final String PERCENT_REGEXP = "[0-9]{2}[1-9]";
    private static final String CONTRACT_NUMBER_REGEXP = "[0-9]{13}";
    private static final String MONEY_REGEXP = "[0-9]{2,}";

    private UUID id;

    @NotBlank(message = "Deposit type is not valid")
    private DepositType depositType;

    @NotBlank(message = "Contract number is not valid")
    @Pattern(message = "Contract number is not valid", regexp = CONTRACT_NUMBER_REGEXP)
    private Long contractNumber;

    @NotBlank(message = "Currency is not valid")
    private Currency currency;

    @NotBlank(message = "Start date is not valid")
    @JsonFormat(pattern = DATE_REGEXP, shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;

    @NotBlank(message = "End date is not valid")
    @JsonFormat(pattern = DATE_REGEXP, shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;

    @NotBlank(message = "Contract term is not valid")
    @Pattern(message = "Contract term is not valid", regexp = NUMBER_REGEXP)
    private Integer contractTerm;

    @NotBlank(message = "Percent is not valid")
    @Pattern(message = "Percent is not valid", regexp = PERCENT_REGEXP)
    private BigDecimal percent;

    @NotBlank(message = "Sum amount is not valid")
    @Pattern(message = "Sum amount is not valid", regexp = MONEY_REGEXP)
    private BigDecimal sumAmount;

    @NotBlank(message = "Client id is not valid")
    private UUID clientId;

    private ClientDto client;

    private BankAccountDto currentAccount;

    private BankAccountDto percentAccount;
}
