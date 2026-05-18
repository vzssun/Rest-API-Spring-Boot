package restapi.spring.project.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
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

   
   // Pegar livro pelo id
   @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookModel>> getBookById(@PathVariable Long bookId) {
        Optional<BookModel> book = bookService.getBookById(bookId);

        return book.map(b -> ResponseEntity.ok(ApiResponse.success("Book found", b)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("Book not found")));
    }

    // Pegar todos os livros 
    @GetMapping
    public ApiResponse<PaginatedResponse<BookDTO>> getAllBooks(Pageable pageable) {
        return ApiResponse.success("Books fetched successfully", bookService.getAllBooks(pageable));
    }
    // Pegar livro pelo nome
    @GetMapping("/search")
    public ApiResponse<PaginatedResponse<BookDTO>> getBooksByName(Pageable pageable, @RequestParam String name) {
        return ApiResponse.success("Books found", bookService.getBooksByName(pageable, name));

    }
    // Pegar livros por categoria
    @GetMapping("/category/{category}")
    public ApiResponse<PaginatedResponse<BookDTO>> getBooksByCategory(Pageable pageable, @PathVariable String category){
        return ApiResponse.success("Books Category fetched successfully", bookService.getBooksByCategory(pageable, category));
    }

    @GetMapping("/search/category")
    public ApiResponse<PaginatedResponse<BookDTO>> getBooksByNameAndCategory(Pageable pageable,@RequestParam String name, @RequestParam String category) {
        return ApiResponse.success("Books found", bookService.getBooksByNameAndCategory(pageable, name, category));
    }

    // pegar livros com Disponibilidade mas não tem paginação nem filtro de categoria

    @GetMapping("/available")
    public ApiResponse<PaginatedResponse<BookDTO>> getAvailableBooks(Pageable pageable, @RequestParam boolean available) {
        return ApiResponse.success("Available books found", bookService.getBooksByAvailability(pageable, available));
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