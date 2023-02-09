package by.zelezinsky.piris.repository;

import by.zelezinsky.piris.model.deposit.Deposit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, UUID> {

    Page<Deposit> findAllByOrderByStartDateDesc(Pageable pageable);
}
