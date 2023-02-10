package by.zelezinsky.piris.service.deposit;

import by.zelezinsky.piris.dto.deposit.DepositDto;
import by.zelezinsky.piris.dto.deposit.DepositDtoMapper;
import by.zelezinsky.piris.exception.BusinessException;
import by.zelezinsky.piris.exception.NotFoundException;
import by.zelezinsky.piris.model.ImaginaryTime;
import by.zelezinsky.piris.model.account.AccountActivityType;
import by.zelezinsky.piris.model.account.AccountCodeType;
import by.zelezinsky.piris.model.account.BankAccount;
import by.zelezinsky.piris.model.client.Client;
import by.zelezinsky.piris.model.deposit.Deposit;
import by.zelezinsky.piris.model.deposit.DepositType;
import by.zelezinsky.piris.repository.BankAccountRepository;
import by.zelezinsky.piris.repository.ClientRepository;
import by.zelezinsky.piris.repository.DepositRepository;
import by.zelezinsky.piris.repository.ImaginaryTimeRepository;
import by.zelezinsky.piris.service.imaginarytime.ImaginaryTimeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private static final BigDecimal PERCENT_SCALE_VALUE = BigDecimal.valueOf(10 * 100);
    private static final BigDecimal MONTH_AMOUNT = BigDecimal.valueOf(12);
    private static final int END_VALUE = 9;
    private static final int FIRST_DAY = 1;
    private final DepositRepository depositRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DepositDtoMapper depositDtoMapper;
    private final ClientRepository clientRepository;
    private final ImaginaryTimeRepository imaginaryTimeRepository;
    private final ImaginaryTimeService imaginaryTimeService;

    @Override
    @Transactional
    public DepositDto create(DepositDto dto) {
        Deposit deposit = depositDtoMapper.toEntity(dto);
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();
        dto.setStartDate(LocalDate.of(startDate.getYear(), startDate.getMonthValue(), FIRST_DAY));
        dto.setEndDate(LocalDate.of(endDate.getYear(), endDate.getMonthValue(), FIRST_DAY));
        Client client = findClientById(dto.getClientId());

        BankAccount bankCashAccount = findBankCashAccount();
        bankCashAccount.setDebit(bankCashAccount.getDebit().add(dto.getSumAmount()));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(dto.getSumAmount()));

        DepositType depositType = dto.getDepositType();
        BankAccount currentAccount = createCurrentAccount(dto.getSumAmount(), client, depositType.toString());
        BankAccount percentAccount = createPercentAccount(client, depositType.toString());

        BankAccount bankFundAccount = findBankFundAccount();
        bankFundAccount.setCredit(bankFundAccount.getCredit().add(dto.getSumAmount()));

        bankAccountRepository.saveAll(List.of(percentAccount, currentAccount, bankCashAccount, bankFundAccount));
        deposit.setPercentAccount(percentAccount);
        deposit.setCurrentAccount(currentAccount);
        deposit.setClient(client);
        return depositDtoMapper.toDto(depositRepository.save(deposit));
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
        return depositRepository.findAllByIsOpenIsTrueOrderByStartDateDesc(pageable).map(depositDtoMapper::toDto);
    }

    @Override
    @Transactional
    public void closeDeposit(UUID id) {
        Deposit deposit = findDepositById(id);
        ImaginaryTime date = imaginaryTimeService.getDate();
        if (deposit.getDepositType().equals(DepositType.IRREVOCABLE)
                && deposit.getEndDate().isAfter(date.getCurrentDate())) {
            throw new BusinessException("Irrevocable deposit can not be closed");
        } else if (deposit.getIsOpen().equals(Boolean.FALSE)) {
            throw new BusinessException("Deposit has been closed");
        }
        BigDecimal depositSum = deposit.getSumAmount();
        BankAccount bankFundAccount = findBankFundAccount();
        bankFundAccount.setDebit(bankFundAccount.getDebit().subtract(depositSum));

        BankAccount currentAccount = deposit.getCurrentAccount();
        currentAccount.setCredit(currentAccount.getCredit().add(depositSum));
        currentAccount.setDebit(currentAccount.getDebit().subtract(depositSum));

        BankAccount bankCashAccount = findBankCashAccount();
        bankCashAccount.setDebit(bankCashAccount.getDebit().add(depositSum));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(depositSum));

        bankAccountRepository.saveAll(List.of(bankFundAccount, currentAccount, bankCashAccount));
        deposit.setIsOpen(false);
        depositRepository.save(deposit);
    }

    @Override
    @Transactional
    public void closeDay(Integer monthAmount) {
        ImaginaryTime date = imaginaryTimeService.getDate();
        LocalDate previousDate = date.getCurrentDate();
        LocalDate currentDate = date.getCurrentDate().plusMonths(monthAmount);
        BankAccount bankFundAccount = findBankFundAccount();
        BankAccount bankCashAccount = findBankCashAccount();
        date.setCurrentDate(currentDate);
        imaginaryTimeRepository.save(date);
        List<Deposit> deposits = depositRepository.findAllByIsOpenIsTrueOrderByStartDateDesc(Pageable.unpaged()).get()
                .map(deposit -> calculatePercents(deposit, previousDate, currentDate, bankFundAccount, bankCashAccount))
                .toList();
        List<BankAccount> accounts = new java.util.ArrayList<>(deposits
                .stream()
                .map(deposit -> List.of(deposit.getCurrentAccount(), deposit.getPercentAccount()))
                .flatMap(Collection::stream).toList());
        accounts.addAll(List.of(bankCashAccount, bankFundAccount));
        bankAccountRepository.saveAll(accounts);
        depositRepository.saveAll(deposits);
    }

    private Deposit calculatePercents(Deposit deposit, LocalDate previousDate, LocalDate currentDate,
                                   BankAccount bankFundAccount, BankAccount bankCashAccount) {
        BigDecimal monthPercents = deposit.getPercent().divide(PERCENT_SCALE_VALUE, RoundingMode.DOWN)
                .multiply(deposit.getSumAmount())
                .divide(MONTH_AMOUNT, RoundingMode.HALF_EVEN);

        LocalDate startDate = previousDate.isAfter(deposit.getStartDate())
                ? previousDate
                : deposit.getStartDate();
        LocalDate endDate = currentDate.isAfter(deposit.getEndDate())
                ? deposit.getEndDate()
                : currentDate;
        Period period = Period.between(startDate, endDate);
        int months = (int) period.toTotalMonths();
        System.out.println(months);

        BigDecimal percentMoney = monthPercents.multiply(BigDecimal.valueOf(months));

        bankFundAccount.setDebit(bankFundAccount.getDebit().subtract(percentMoney));

        BankAccount percentAccount = deposit.getPercentAccount();
        percentAccount.setCredit(percentAccount.getCredit().add(percentMoney));
        percentAccount.setDebit(percentAccount.getDebit().subtract(percentMoney));

        bankCashAccount.setDebit(bankCashAccount.getDebit().add(percentMoney));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(percentMoney));

        if (deposit.getEndDate().isBefore(currentDate) || deposit.getEndDate().isEqual(currentDate)) {
            closeDeposit(deposit, bankCashAccount, bankFundAccount);
        }
        return deposit;
    }

    private void closeDeposit(Deposit deposit, BankAccount bankCashAccount, BankAccount bankFundAccount) {
        BigDecimal depositSum = deposit.getSumAmount();
        bankFundAccount.setDebit(bankFundAccount.getDebit().subtract(depositSum));

        BankAccount currentAccount = deposit.getCurrentAccount();
        currentAccount.setCredit(currentAccount.getCredit().add(depositSum));
        currentAccount.setDebit(currentAccount.getDebit().subtract(depositSum));

        bankCashAccount.setDebit(bankCashAccount.getDebit().add(depositSum));
        bankCashAccount.setCredit(bankCashAccount.getCredit().subtract(depositSum));

        deposit.setIsOpen(false);
    }

    private Deposit findDepositById(UUID id) {
        return depositRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Deposit with id: %s not found", id)));
    }

    private Client findClientById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %s not found", id)));
    }

    private BankAccount createPercentAccount(Client client, String prefix) {
        return BankAccount.builder()
                .credit(BigDecimal.ZERO)
                .debit(BigDecimal.ZERO)
                .activityType(AccountActivityType.PASSIVE)
                .codeType(AccountCodeType.PERSONAL)
                .number(AccountCodeType.PERSONAL.getCode().toString().concat(createRandomNumber()))
                .clientData(prefix.concat(client.getFirstName().concat(StringUtils.SPACE).concat(client.getMiddleName()
                        .concat(StringUtils.SPACE).concat(client.getLastName()))))
                .id(UUID.randomUUID())
                .build();
    }

    private BankAccount createCurrentAccount(BigDecimal amount, Client client, String prefix) {
        return BankAccount.builder()
                .credit(amount)
                .debit(BigDecimal.ZERO.subtract(amount))
                .activityType(AccountActivityType.PASSIVE)
                .codeType(AccountCodeType.PERSONAL)
                .number(AccountCodeType.PERSONAL.getCode().toString().concat(createRandomNumber()))
                .clientData(prefix.concat(client.getFirstName().concat(StringUtils.SPACE).concat(client.getMiddleName()
                        .concat(StringUtils.SPACE).concat(client.getLastName()))))
                .id(UUID.randomUUID())
                .build();
    }

    private String createRandomNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < END_VALUE; i++) {
            sb.append((int) Math.ceil(Math.random() * 10));
        }
        return sb.toString();
    }

    private BankAccount findBankFundAccount() {
        return bankAccountRepository.findByCodeType(AccountCodeType.BANK_FUND);
    }

    private BankAccount findBankCashAccount(){
        return bankAccountRepository.findByCodeType(AccountCodeType.BANK_CASH);
    }
}
