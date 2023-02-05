package by.zelezinsky.piris.controller;

import by.zelezinsky.piris.dto.client.ClientDto;
import by.zelezinsky.piris.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Client Controller")
@RestController
@RequestMapping(Url.Client.PATH)
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Get all clients")
    @GetMapping
    @PageableAsQueryParam
    public Page<ClientDto> findAll(@Parameter(hidden = true) Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @Operation(summary = "Create client")
    @PostMapping
    public ClientDto create(@RequestBody ClientDto dto) {
        return clientService.create(dto);
    }

    @Operation(summary = "Update client by id")
    @PutMapping(Url.ID)
    public ClientDto update(@PathVariable UUID id, @RequestBody ClientDto dto) {
        return clientService.update(id, dto);
    }

    @Operation(summary = "Delete client by id")
    @DeleteMapping(Url.ID)
    public void delete(@PathVariable UUID id) {
        clientService.delete(id);
    }

    @Operation(summary = "Get client by id")
    @GetMapping(Url.ID)
    public ClientDto findById(@PathVariable UUID id) {
        return clientService.findById(id);
    }
}
