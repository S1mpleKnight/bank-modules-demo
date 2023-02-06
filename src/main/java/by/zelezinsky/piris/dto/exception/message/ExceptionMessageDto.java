package by.zelezinsky.piris.dto.exception.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionMessageDto {

    private String message;
    private String cause;
}
