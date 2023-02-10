package by.zelezinsky.piris.service.imaginarytime;

import by.zelezinsky.piris.model.ImaginaryTime;
import by.zelezinsky.piris.repository.ImaginaryTimeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImaginaryTimeServiceImpl implements ImaginaryTimeService {

    private static final Integer FIRST_DAY_OF_MONTH = 1;
    private static final Integer CREATED_TIME_ORDER = 0;
    private final ImaginaryTimeRepository imaginaryTimeRepository;

    @Override
    public ImaginaryTime getDate() {
        List<ImaginaryTime> all = imaginaryTimeRepository.findAll();
        if (CollectionUtils.isEmpty(all)) {
            ImaginaryTime time = new ImaginaryTime();
            time.setCurrentDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
                    FIRST_DAY_OF_MONTH));
            return imaginaryTimeRepository.save(time);
        } else {
            return all.get(CREATED_TIME_ORDER);
        }
    }
}
