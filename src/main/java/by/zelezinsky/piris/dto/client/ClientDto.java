package by.zelezinsky.piris.dto.client;

import by.zelezinsky.piris.dto.passport.PassportDto;
import by.zelezinsky.piris.model.client.DisabilityStatus;
import by.zelezinsky.piris.model.client.FamilyStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ClientDto {

    private final static String NAME_REGEXP = "[a-zA-Zа-яА-Я]{1,255}";
    private final static String ADDRESS_REGEXP = "[a-zA-Zа-яА-Я]+[a-zA-Zа-яА-Я0-9 .,-]{4,254}";
    private final static String MOBILE_PHONE_NUMBER_REGEXP = "(((\\+)375)|(80))(25|29|33|44)[0-9]{7}";
    private final static String HOME_PHONE_NUMBER_REGEXP = "(80)[0-9]{3}[0-9]{7}";
    private static final String DATE_REGEXP = "yyyy-MM-dd";
    private static final String CITY_REGEXP = "[a-zA-zа-яА-Я -]{2,255}";
    private static final String MONEY_REGEXP = "([0-9]+([.][0-9])?)?";

    private UUID id;

    @NotBlank(message = "First name can not be null")
    @Pattern(message = "First name is not valid", regexp = NAME_REGEXP)
    private String firstName;

    @Pattern(message = "Last name is not valid", regexp = NAME_REGEXP)
    @NotBlank(message = "Last name can not be null")
    private String lastName;

    @Pattern(message = "Middle name is not valid", regexp = NAME_REGEXP)
    @NotBlank(message = "Middle name can not be null")
    private String middleName;

    @NotBlank(message = "Birthday can not be null")
    @JsonFormat(pattern = DATE_REGEXP, shape = JsonFormat.Shape.STRING)
    private LocalDate birthday;

    @NotNull(message = "Sex field is not valid")
    private Boolean isMale;

    private UUID passportId;

    private @Valid PassportDto passport;

    @NotBlank(message = "Birthday place can not be null")
    @Pattern(message = "Birthday place is not valid", regexp = ADDRESS_REGEXP)
    private String birthPlace;

    @NotBlank(message = "City name can not be null")
    @Pattern(message = "City name is not valid", regexp = CITY_REGEXP)
    private String livingCity;

    @Pattern(message = "Living address is not valid", regexp = ADDRESS_REGEXP)
    private String livingAddress;

    @Pattern(message = "Home phone is not valid", regexp = HOME_PHONE_NUMBER_REGEXP)
    private String homePhoneNumber;

    @Pattern(message = "Mobile phone is not valid", regexp = MOBILE_PHONE_NUMBER_REGEXP)
    private String mobilePhoneNumber;

    @Email(message = "Invalid e-mail")
    private String email;

    @NotBlank(message = "Family status can not be null")
    private FamilyStatus familyStatus;

    @NotBlank(message = "Nationality can not be null")
    @Pattern(message = "Nationality is not valid", regexp = NAME_REGEXP)
    private String nationality;

    @NotBlank(message = "Disability status can not be null")
    private DisabilityStatus disabilityStatus;

    @NotBlank(message = "Pensioner flag can not be null")
    private Boolean pensioner;

    @Pattern(message = "Invalid month income value", regexp = MONEY_REGEXP )
    private BigDecimal monthlyIncome;

    @NotBlank(message = "Military service flag can not be null")
    private Boolean isLiableMilitaryService;
}
