package by.zelezinsky.piris.service.deposit;

import by.zelezinsky.piris.dto.account.BankAccountDtoMapper;
import by.zelezinsky.piris.dto.deposit.DepositDto;
import by.zelezinsky.piris.dto.deposit.DepositDtoMapper;
import by.zelezinsky.piris.exception.NotFoundException;
import by.zelezinsky.piris.model.account.AccountActivityType;
import by.zelezinsky.piris.model.account.AccountCodeType;
import by.zelezinsky.piris.model.account.BankAccount;
import by.zelezinsky.piris.model.client.Client;
import by.zelezinsky.piris.model.deposit.Deposit;
import by.zelezinsky.piris.repository.BankAccountRepository;
import by.zelezinsky.piris.repository.ClientRepository;
import by.zelezinsky.piris.repository.DepositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private static final int END_VALUE = 9;
    private final DepositRepository depositRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DepositDtoMapper depositDtoMapper;
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public DepositDto create(DepositDto dto) {
        Deposit deposit = depositDtoMapper.toEntity(dto);
        Client client = findClientById(dto.getClientId());
        BankAccount percentAccount = createPercentAccount();
        BankAccount currentAccount = createCurrentAccount(dto.getSumAmount());
        bankAccountRepository.saveAll(List.of(percentAccount, currentAccount));
        deposit.setPercentAccount(percentAccount);
        deposit.setCurrentAccount(currentAccount);
        deposit.setClient(client);
        return depositDtoMapper.toDto(depositRepository.save(deposit));
    }

    private BankAccount createPercentAccount() {
        return BankAccount.builder()
                .credit(BigDecimal.ZERO)
                .debit(BigDecimal.ZERO)
                .activityType(AccountActivityType.PASSIVE)
                .codeType(AccountCodeType.PERSONAL)
                .number(AccountCodeType.PERSONAL.getCode().toString().concat(createRandomNumber()))
                .build();
    }

    private BankAccount createCurrentAccount(BigDecimal amount) {
        return BankAccount.builder()
                .credit(amount)
                .debit(BigDecimal.ZERO)
                .activityType(AccountActivityType.PASSIVE)
                .codeType(AccountCodeType.PERSONAL)
                .number(AccountCodeType.PERSONAL.getCode().toString().concat(createRandomNumber()))
                .build();
    }

    private String createRandomNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < END_VALUE; i++) {
            sb.append((int) Math.ceil(Math.random() * 10));
        }
        return sb.toString();
    }

    @Override
    public DepositDto update(UUID id, DepositDto dto) {
        return null;
    }

    @Override
    public void delete(UUID id) {
        Deposit deposit = findDepositById(id);
        depositRepository.delete(deposit);
    }

    @Override
    public DepositDto findById(UUID id) {
        return depositDtoMapper.toDto(findDepositById(id));
    }

    @Override
    public Page<DepositDto> findAll(Pageable pageable) {
        return depositRepository.findAllByOrderByStartDateDesc(pageable).map(depositDtoMapper::toDto);
    }

    private Deposit findDepositById(UUID id) {
        return depositRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Deposit with id: %s not found", id)));
    }

    private Client findClientById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %s not found", id)));
    }
}
