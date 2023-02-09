package by.zelezinsky.piris.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountCodeType {
    BANK_CASH(1010),
    BANK_FUND(7327),
    PERSONAL(3014);

    private final Integer code;
}
