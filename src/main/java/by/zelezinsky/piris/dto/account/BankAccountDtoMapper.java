package by.zelezinsky.piris.dto.account;

import by.zelezinsky.piris.model.account.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountDtoMapper {

    BankAccountDto toDto(BankAccount account);
}
