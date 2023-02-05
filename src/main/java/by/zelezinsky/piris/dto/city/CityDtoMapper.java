package by.zelezinsky.piris.dto.city;

import by.zelezinsky.piris.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityDtoMapper {

    @Mapping(target = "id", ignore = true)
    City toEntity(CityDto dto);

    CityDto toDto(City client);
}
