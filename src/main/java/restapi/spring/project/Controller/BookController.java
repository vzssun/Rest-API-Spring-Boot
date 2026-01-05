package restapi.spring.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import restapi.spring.project.Dto.request.BookReservationRequest;
import restapi.spring.project.Dto.response.BookReservationResponse;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Model.UserModel;
import restapi.spring.project.Services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookModel> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Long bookId) {
        Optional<BookModel> book = bookService.getBookById(bookId);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<BookModel> createBook(@RequestBody BookModel book) {
        BookModel savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long bookId, @RequestBody BookModel bookDetails) {
        BookModel updatedBook = bookService.updateBook(bookId, bookDetails);
        return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<List<BookModel>> getAllBookReservations(@PathVariable Long reservationId) {
        List<BookModel> reservations = bookService.getAllBookReservations(reservationId);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("{bookId}/{userId}/reserve")
    public ResponseEntity<BookReservationResponse> reserveBook(@PathVariable Long bookId ,@PathVariable Long userId, @RequestBody BookReservationRequest request) {
        BookReservationResponse response = bookService.reserveBook(bookId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

