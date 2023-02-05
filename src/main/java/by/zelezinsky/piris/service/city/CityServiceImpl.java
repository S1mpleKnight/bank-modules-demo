package by.zelezinsky.piris.service.city;

import by.zelezinsky.piris.dto.city.CityDto;
import by.zelezinsky.piris.dto.city.CityDtoMapper;
import by.zelezinsky.piris.model.City;
import by.zelezinsky.piris.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityDtoMapper mapper;

    @Override
    public City create(CityDto dto) {
        Optional<City> optionalCity = cityRepository.findByName(dto.getName());
        if (optionalCity.isEmpty()) {
            return cityRepository.save(mapper.toEntity(dto));
        } else {
            return optionalCity.get();
        }
    }

    @Override
    public CityDto update(CityDto dto) {
        return null;
    }

    @Override
    public Page<CityDto> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public CityDto findById(UUID id) {
        return null;
    }
}
