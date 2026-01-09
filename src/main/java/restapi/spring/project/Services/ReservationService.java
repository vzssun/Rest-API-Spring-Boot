package restapi.spring.project.Services;
import org.springframework.stereotype.Service;
import restapi.spring.project.Dto.request.BookReservationRequest;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Model.ReservationModel;
import restapi.spring.project.Model.UserModel;
import restapi.spring.project.Repository.BookRepository;
import restapi.spring.project.Repository.ReservationRepository;
import restapi.spring.project.Repository.UserRepository;
import restapi.spring.project.Exception.ResourceNotFoundException;
import restapi.spring.project.Exception.ConflictException;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
            }

    public List<BookModel> getAllBooksByReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .map(ReservationModel::getBooks) // get books list
            .orElse(List.of()); // empty list if reservation not found
}


    public ReservationModel reserveBook(Long userId, BookReservationRequest request) {
    // Fetch user
    UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    // Create reservation
    ReservationModel reservation = new ReservationModel();
    reservation.setUser(user);
    reservation.setReservationPeriod(request.getReservationPeriod());

    // Save reservation first so it has an ID
    reservationRepository.save(reservation);

    // Process each book
    for (BookReservationRequest.BookInfo bookInfo : request.getBooks()) {
        BookModel book = bookRepository.findById(bookInfo.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book with ID " + bookInfo.getBookId() + " not found"));

        if (!book.isAvailable()) {
            throw new ConflictException("Book '" + book.getTitle() + "' is already reserved");
        }

        // Link book to reservation
        book.setReservation(reservation);
        book.setAvailable(false);

        bookRepository.save(book);
    }

    return reservation;
}

    public void cancelReservation(Long reservationId) {
    ReservationModel reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

    // Set books as available
    for (BookModel book : reservation.getBooks()) {
        book.setAvailable(true);
        book.setReservation(null);
        bookRepository.save(book);
    }

    reservationRepository.delete(reservation);
    }

    // Get all reservations
    public List<ReservationModel> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Get reservation by ID
    public ReservationModel getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation with ID " + reservationId + " not found"));
    }
}
