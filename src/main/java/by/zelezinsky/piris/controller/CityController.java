package by.zelezinsky.piris.controller;

import by.zelezinsky.piris.dto.city.CityDto;
import by.zelezinsky.piris.service.city.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "City Controller")
@RestController
@RequestMapping(Url.City.PATH)
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Get all cities")
    public Page<CityDto> findAll(@Parameter(hidden = true) Pageable pageable) {
        return cityService.findAll(pageable);
    }
}
