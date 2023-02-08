package by.zelezinsky.piris.service.client;

import by.zelezinsky.piris.dto.client.ClientDto;
import by.zelezinsky.piris.dto.client.ClientDtoMapper;
import by.zelezinsky.piris.dto.passport.PassportDto;
import by.zelezinsky.piris.dto.passport.PassportDtoMapper;
import by.zelezinsky.piris.exception.BusinessException;
import by.zelezinsky.piris.exception.NotFoundException;
import by.zelezinsky.piris.model.City;
import by.zelezinsky.piris.model.Client;
import by.zelezinsky.piris.model.Passport;
import by.zelezinsky.piris.repository.ClientRepository;
import by.zelezinsky.piris.repository.PassportRepository;
import by.zelezinsky.piris.service.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;
    private final PassportRepository passportRepository;
    private final CityService cityService;
    private final PassportDtoMapper passportDtoMapper;

    @Override
    @Transactional
    public ClientDto create(ClientDto dto) {
        City city = cityService.findByName(dto.getLivingCity());
        Optional<Client> createdClient = clientRepository
                .findByFirstNameAndLastNameAndMiddleName(dto.getFirstName(), dto.getLastName(), dto.getMiddleName());
        if (createdClient.isPresent()) {
            throw new BusinessException(String.format("Client with name: %s %s %s is already exists",
                    dto.getFirstName(), dto.getMiddleName(), dto.getLastName()));
        }
        PassportDto passportDto = dto.getPassport();
        Optional<Passport> optionalPassport = passportRepository
                .findByIdentityNumber(passportDto.getIdentityNumber());
        if (optionalPassport.isPresent()) {
            throw new BusinessException(String.format("Client with passport identity number: %s is already exists",
                    passportDto.getIdentityNumber()));
        }
        Optional<Passport> clonePassport = passportRepository
                .findByPassportSeriesAndIssuingAuthorityAndIssueDateAndPassportNumber(
                        passportDto.getPassportSeries(), passportDto.getIssuingAuthority(), passportDto.getIssueDate(),
                        passportDto.getPassportNumber());
        if (clonePassport.isPresent()) {
            throw new BusinessException("Client with that password is already exists");
        }
        Client client = clientDtoMapper.toEntity(dto);
        client.setLivingCity(city);
        client = clientRepository.save(client);
        Passport passport = passportDtoMapper.toEntity(dto.getPassport());
        passport.setClient(client);
        passportRepository.save(passport);
        return clientDtoMapper.toDto(client);
    }

    @Override
    @Transactional
    public ClientDto update(UUID id, ClientDto dto) {
        Client clientById = findClientById(id);
        City city = cityService.findByName(dto.getLivingCity());
        Client client = clientDtoMapper.toEntity(clientById, dto);
        client.setLivingCity(city);
        client = clientRepository.save(client);
        Passport passport = passportDtoMapper.toEntity(dto.getPassport(), clientById.getPassport());
        passport.setClient(client);
        passportRepository.save(passport);
        return clientDtoMapper.toDto(client);
    }

    @Override
    public void delete(UUID id) {
        Client clientById = findClientById(id);
        clientRepository.delete(clientById);
    }

    @Override
    public Page<ClientDto> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientDtoMapper::toDto);
    }

    @Override
    public ClientDto findById(UUID id) {
        return clientDtoMapper.toDto(findClientById(id));
    }

    private Client findClientById(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client does not exist"));
    }
}
