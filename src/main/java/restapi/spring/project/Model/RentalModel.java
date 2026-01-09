package restapi.spring.project.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rentals")
public class RentalModel {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    public RentalModel() {
    }

    public RentalModel(Long userId, Long bookId, LocalDate rentalDate, LocalDate returnDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
}
