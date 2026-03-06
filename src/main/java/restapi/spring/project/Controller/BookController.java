package restapi.spring.project.Controller;

import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import restapi.spring.project.Dto.response.ApiResponse.*;
import restapi.spring.project.Dto.BookDTO;
import restapi.spring.project.Dto.response.ApiResponse;
import restapi.spring.project.Dto.response.PaginatedResponse;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Services.BookService;
import restapi.spring.project.Repository.BookRepository;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    /*
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
 */


    /*
    @GetMapping
    public ApiResponse<PaginatedResponse<BookModel>> getByCategory(String category,Pageable pageable){
        return ApiResponse.success("Books Category fetched successfully", bookService.getBookByCategory(category, pageable));

    }
    */

    @GetMapping
    public ApiResponse<PaginatedResponse<BookDTO>> getBooks(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String category
    ){
        return ApiResponse.success("Books Category fetched successfully", bookService.getBooks(pageable, search, category));
    }


    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookModel>> getBookById(@PathVariable Long bookId) {
        Optional<BookModel> book = bookService.getBookById(bookId);

        return book.map(b -> ResponseEntity.ok(ApiResponse.success("Book found", b)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("Book not found")));
    }

    //@PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    @PostMapping
    public ResponseEntity<ApiResponse<BookModel>> createBook(@RequestBody BookModel book) {
        BookModel savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Book created successfully", savedBook));
    }

    @PreAuthorize("hasAnyAuthority('admin:update', 'management:update')")
    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookModel>> updateBook(@PathVariable Long bookId, @RequestBody BookModel bookDetails) {
        return bookService.updateBook(bookId, bookDetails)
            .map(book -> ResponseEntity.ok(ApiResponse.success("Updated", book)))
            .orElse(ResponseEntity.status(404).body(ApiResponse.failure("Not found")));
    }

    @PreAuthorize("hasAnyAuthority('admin:delete', 'management:delete')")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);

        if (!bookService.getBookById(bookId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("Book not found"));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Book deleted successfully", null));
    }
}