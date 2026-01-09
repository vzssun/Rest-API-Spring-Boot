package restapi.spring.project.Dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalRequest {

    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    public RentalRequest() {
    }

    public RentalRequest(Long userId, Long bookId, LocalDate rentalDate, LocalDate returnDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
}
