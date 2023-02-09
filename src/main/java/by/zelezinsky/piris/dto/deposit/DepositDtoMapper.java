package by.zelezinsky.piris.dto.deposit;

import by.zelezinsky.piris.dto.account.BankAccountDtoMapper;
import by.zelezinsky.piris.dto.client.ClientDtoMapper;
import by.zelezinsky.piris.model.deposit.Deposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BankAccountDtoMapper.class, ClientDtoMapper.class})
public interface DepositDtoMapper {

    @Mapping(target = "clientId", source = "client.id")
    DepositDto toDto(Deposit deposit);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentAccount", ignore = true)
    @Mapping(target = "percentAccount", ignore = true)
    @Mapping(target = "client", ignore = true)
    Deposit toEntity(DepositDto dto);
}
