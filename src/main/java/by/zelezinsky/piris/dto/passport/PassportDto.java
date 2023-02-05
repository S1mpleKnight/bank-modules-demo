package by.zelezinsky.piris.dto.passport;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Valid
public class PassportDto {

    private static final String PASSPORT_SERIES_REGEXP = "[A-Z]{2}";
    private static final String PASSPORT_NUMBER_REGEXP = "[0-9]{7}";
    private static final String PASSPORT_ISSUING_AUTHORITY_REGEXP = "[a-zA-Zа-яА-Я -]{4}";
    private static final String DATE_REGEXP = "YYYY-MM-DD";
    private static final String IDENTITY_NUMBER_REGEXP = "[0-9A-Z]{14}";

    @NotBlank(message = "Passport series can not be null")
    @Pattern(message = "Passport series is not valid", regexp = PASSPORT_SERIES_REGEXP)
    private String passportSeries;
    @NotBlank(message = "Passport number can not be null")
    @Pattern(message = "Passport number is not valid", regexp = PASSPORT_NUMBER_REGEXP)
    private String passportNumber;
    @NotBlank(message = "Issuing authority can not be null")
    @Pattern(message = "Issuing authority is not valid", regexp = PASSPORT_ISSUING_AUTHORITY_REGEXP)
    private String issuingAuthority;
    @NotBlank(message = "Issue date can not be null")
    @JsonFormat(pattern = DATE_REGEXP, shape = JsonFormat.Shape.STRING)
    private LocalDate issueDate;
    @NotBlank(message = "Identity number can not be null")
    @Pattern(message = "Identity number is not valid", regexp = IDENTITY_NUMBER_REGEXP)
    private String identityNumber;
}
