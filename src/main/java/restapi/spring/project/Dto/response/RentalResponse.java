package restapi.spring.project.Dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
}
