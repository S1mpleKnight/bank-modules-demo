package by.zelezinsky.piris.dto.city;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private static final String CITY_REGEXP = "[a-zA-zа-яА-Я -]{2,}";

    @NotBlank(message = "City name can not be null")
    @Pattern(message = "City name is not valid", regexp = CITY_REGEXP)
    private String name;

    private UUID id;
}
