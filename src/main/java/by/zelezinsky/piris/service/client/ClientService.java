package by.zelezinsky.piris.service.client;

import by.zelezinsky.piris.dto.client.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClientService {

    ClientDto create(ClientDto dto);

    ClientDto update(UUID id, ClientDto dto);

    void delete(UUID id);

    Page<ClientDto> findAll(Pageable pageable);

    ClientDto findById(UUID id);
}
