package by.zelezinsky.piris.dto.client;

import by.zelezinsky.piris.dto.city.CityDtoMapper;
import by.zelezinsky.piris.dto.passport.PassportDtoMapper;
import by.zelezinsky.piris.model.client.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CityDtoMapper.class, PassportDtoMapper.class})
public interface ClientDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livingCity", ignore = true)
    @Mapping(target = "passport", ignore = true)
    Client toEntity(ClientDto dto);

    @Mapping(target = "passportId", source = "passport.id")
    @Mapping(target = "livingCity", source = "livingCity.name")
    ClientDto toDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livingCity", ignore = true)
    @Mapping(target = "passport", ignore = true)
    Client toEntity(@MappingTarget Client client, ClientDto dto);
}
