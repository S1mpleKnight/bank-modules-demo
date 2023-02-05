package by.zelezinsky.piris.repository;

import by.zelezinsky.piris.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassportRepository extends JpaRepository<Passport, UUID> {

    Optional<Passport> findByIdentityNumber(String number);

    Optional<Passport> findByPassportSeriesAndIssuingAuthorityAndIssueDateAndPassportNumber(
            String passwordSeries, String issuingAuthority, LocalDate issueDate, String passportNumber
    );
}
