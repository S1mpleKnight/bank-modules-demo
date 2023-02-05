package by.zelezinsky.piris.controller;

import by.zelezinsky.piris.dto.client.ClientDto;
import by.zelezinsky.piris.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(Url.Client.PATH)
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public Page<ClientDto> findAll(Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @PostMapping
    public ClientDto create(@RequestBody ClientDto dto) {
        return clientService.create(dto);
    }

    @PutMapping(Url.ID)
    public ClientDto update(@PathVariable UUID id, @RequestBody ClientDto dto) {
        return clientService.update(id, dto);
    }

    @DeleteMapping(Url.ID)
    public void delete(@PathVariable UUID id) {
        clientService.delete(id);
    }

    @GetMapping(Url.ID)
    public ClientDto findById(@PathVariable UUID id) {
        return clientService.findById(id);
    }
}
