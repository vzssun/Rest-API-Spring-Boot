package restapi.spring.project.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookReservationRequest {
    private String title;
    private String author;
    private String reserverName;
    private String reservationPeriod;
}
