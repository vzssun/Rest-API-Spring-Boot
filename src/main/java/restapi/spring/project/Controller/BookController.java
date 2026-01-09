package restapi.spring.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import restapi.spring.project.Dto.response.ApiResponse;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookModel>>> getAllBooks() {

    List<BookModel> books = bookService.getAllBooks();

    if (books.isEmpty()) {
        return ResponseEntity.ok(
            ApiResponse.success("No books available", List.of())
        );
    }

    return ResponseEntity.ok(
        ApiResponse.success(
            "Found " + books.size() + " books",
            books
        )
    );
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookModel>> getBookById(@PathVariable Long bookId) {
        Optional<BookModel> book = bookService.getBookById(bookId);

        return book.map(b -> ResponseEntity.ok(ApiResponse.success("Book found", b)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("Book not found")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookModel>> createBook(@RequestBody BookModel book) {
        BookModel savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Book created successfully", savedBook));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookModel>> updateBook(@PathVariable Long bookId, @RequestBody BookModel bookDetails) {
        BookModel updatedBook = bookService.updateBook(bookId, bookDetails);

        if (updatedBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("Book not found"));
        }

        return updatedBook != null ? ResponseEntity.ok(ApiResponse.success("Book updated successfully", updatedBook)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("Book not found"));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);

        if (!bookService.getBookById(bookId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("Book not found"));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Book deleted successfully", null));
    }
}