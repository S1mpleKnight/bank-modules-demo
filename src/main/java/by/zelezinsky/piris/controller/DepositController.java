package by.zelezinsky.piris.controller;

import by.zelezinsky.piris.dto.deposit.DepositDto;
import by.zelezinsky.piris.service.deposit.DepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Deposit Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping(Url.Deposit.PATH)
public class DepositController {

    private final DepositService depositService;

    @Operation(summary = "Get all open deposits")
    @GetMapping
    @PageableAsQueryParam
    public Page<DepositDto> findAll(@Parameter(hidden = true) Pageable pageable) {
        return depositService.findAll(pageable);
    }

    @Operation(summary = "Get deposit by id")
    @GetMapping(Url.ID)
    public DepositDto findById(@PathVariable UUID id) {
        return depositService.findById(id);
    }

    @Operation(summary = "Create deposit")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositDto create(@RequestBody DepositDto dto) {
        return depositService.create(dto);
    }

    @Operation(summary = "Close deposit")
    @PutMapping(Url.Deposit.Operation.CLOSE_TRANSACTION)
    public void closeDeposit(@PathVariable UUID id) {
        depositService.closeDeposit(id);
    }

    @Operation(summary = "Close Day operation")
    @PutMapping(Url.Deposit.Operation.CLOSE_DAY)
    public void closeDay(@RequestParam Integer monthAmount) {
        depositService.closeDay(monthAmount);
    }

}
