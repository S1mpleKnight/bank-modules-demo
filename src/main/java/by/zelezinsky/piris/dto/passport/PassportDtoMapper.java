package by.zelezinsky.piris.dto.passport;

import by.zelezinsky.piris.model.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassportDtoMapper {

    @Mapping(target = "id", ignore = true)
    Passport toEntity(PassportDto dto);

    PassportDto toDto(Passport passport);
}
