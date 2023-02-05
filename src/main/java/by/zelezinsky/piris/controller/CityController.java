package by.zelezinsky.piris.controller;

import by.zelezinsky.piris.dto.city.CityDto;
import by.zelezinsky.piris.service.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Url.City.PATH)
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public Page<CityDto> findAll(Pageable pageable) {
        return cityService.findAll(pageable);
    }
}
