package restapi.spring.project.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Model.UserModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReservationResponse {
    private Long reservationId;
    private Long bookId;
    private Long userId;
    private String reserverName;
    private String title;
    private String author;
    private String reservationPeriod;
    private boolean isAvailable;

    public BookReservationResponse (BookModel book, UserModel user) {
        this.userId = user.getUserId();
        this.reserverName = book.getReserverName();
        this.reservationId = book.getReservationId();
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.reservationPeriod = book.getReservationPeriod();
        this.isAvailable = book.isAvailable();
    }

}

