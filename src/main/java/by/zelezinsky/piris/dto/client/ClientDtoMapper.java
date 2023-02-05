package by.zelezinsky.piris.dto.client;

import by.zelezinsky.piris.dto.city.CityDtoMapper;
import by.zelezinsky.piris.dto.passport.PassportDtoMapper;
import by.zelezinsky.piris.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CityDtoMapper.class, PassportDtoMapper.class})
public interface ClientDtoMapper {

    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientDto dto);

    @Mapping(target = "passportId", source = "passport.id")
    @Mapping(target = "cityId", source = "city.id")
    ClientDto toDto(Client client);
}
