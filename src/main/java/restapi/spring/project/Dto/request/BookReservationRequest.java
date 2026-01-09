package restapi.spring.project.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReservationRequest {
    private List<BookInfo> books; // List of books to reserve
    private Integer reservationPeriod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookInfo {
        private Long bookId; // existing book ID
        private String title; // optional if you want to allow new book creation
        private String author;
    }
}
