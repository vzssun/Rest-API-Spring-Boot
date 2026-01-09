package restapi.spring.project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service // Treat this class as a Service bean (can be injected into other components)
public class BookService {

    @Autowired // Inject an instance of BookRepository here
    private BookRepository bookRepository;

    public List<BookModel> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<BookModel> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public BookModel saveBook(BookModel book) {
        return bookRepository.save(book);
    }

    public long getAllBooksCount() {
        return bookRepository.count();
    }

    public BookModel updateBook(Long id, BookModel bookDetails) {
        Optional<BookModel> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            BookModel book = existingBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setGenre(bookDetails.getGenre());
            book.setCoverImageUrl(bookDetails.getCoverImageUrl());
            book.setPublishedYear(bookDetails.getPublishedYear());
            book.setDescription(bookDetails.getDescription());
            return bookRepository.save(book);
        } else {
            return null;
        }
    }

//    public List<BookModel> findBookbyTitle(String title) {
//        return bookRepository.findBookbyTitle(title);
//    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookModel> getAllBookReservations(Long reservationId) {
        return bookRepository.findByReservationId(reservationId);
    }

        }

