package by.zelezinsky.piris.service.city;

import by.zelezinsky.piris.dto.city.CityDto;
import by.zelezinsky.piris.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CityService {

    CityDto create(CityDto dto);

    CityDto update(CityDto dto);

    Page<CityDto> findAll(Pageable pageable);

    void delete(UUID id);

    CityDto findById(UUID id);

    City findByName(String name);
}
