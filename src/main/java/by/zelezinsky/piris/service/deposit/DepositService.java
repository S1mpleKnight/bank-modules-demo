package by.zelezinsky.piris.service.deposit;

import by.zelezinsky.piris.dto.deposit.DepositDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DepositService {

    DepositDto create(DepositDto dto);

    DepositDto update(UUID id, DepositDto dto);

    void delete(UUID id);

    DepositDto findById(UUID id);

    Page<DepositDto> findAll(Pageable pageable);

    void closeDeposit(UUID id);

    void closeDay(Integer monthAmount);
}
