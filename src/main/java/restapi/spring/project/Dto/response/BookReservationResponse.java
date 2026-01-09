package restapi.spring.project.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.spring.project.Model.ReservationModel;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReservationResponse {

    private Long reservationId;
    private Long userId;
    private String username;
    private Integer reservationPeriod;
    private List<BookInfo> books; // A list of book details

    // Inner class for book info
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookInfo {
        private Long bookId;
        private String title;
        private String author;
        private boolean isAvailable;
    }

    // Static factory method to convert ReservationModel → DTO
    public static BookReservationResponse from(ReservationModel reservation) {
        List<BookInfo> bookList = reservation.getBooks().stream()
                .map(book -> new BookInfo(
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.isAvailable()
                ))
                .collect(Collectors.toList());

        return new BookReservationResponse(
                reservation.getId(),
                reservation.getUser().getUserId(),
                reservation.getUser().getUsername(),
                reservation.getReservationPeriod(),
                bookList
        );
    }
}
