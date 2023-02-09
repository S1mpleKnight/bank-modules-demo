package by.zelezinsky.piris.dto.passport;

import by.zelezinsky.piris.model.client.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassportDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    Passport toEntity(PassportDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    Passport toEntity(PassportDto dto, @MappingTarget Passport passport);
}
