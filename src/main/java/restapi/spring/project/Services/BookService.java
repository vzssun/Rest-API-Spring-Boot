package restapi.spring.project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.spring.project.Dto.response.PaginatedResponse;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Model.ReservationModel;
import restapi.spring.project.Repository.BookRepository;
import restapi.spring.project.Repository.ReservationRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

@Service // Treat this class as a Service bean (can be injected into other components)
public class BookService {

    @Autowired // Inject an instance of BookRepository here
    private BookRepository bookRepository;

    @Autowired 
    private ReservationRepository reservationRepository;
  //  public List<BookModel> getAllBooks() {
  //      return bookRepository.findAll();
  //  }
  public PaginatedResponse<BookModel> getAllBooks(Pageable pageable){
    Page<BookModel> page = bookRepository.findAll(pageable);
    return buildPaginatedResponse(page);
}

private <T> PaginatedResponse<T> buildPaginatedResponse(Page<T> page){
    PaginatedResponse<T> response = new PaginatedResponse<>();
    response.setData(page.getContent());
    response.setCurrentPage(page.getNumber());
    response.setTotalPages(page.getTotalPages());
    response.setTotalItems(page.getTotalElements());
    response.setPageSize(page.getSize());
    response.setHasNext(page.hasNext());
    response.setHasPrevious(page.hasPrevious());
    return response;
}
    

    public Optional<BookModel> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public BookModel saveBook(BookModel book) {
        return bookRepository.save(book);
    }
public BookModel createBook(BookModel book) {

    if (book.getReservation() != null) {
        Long id = book.getReservation().getId();

        ReservationModel reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        book.setReservation(reservation);
    }

    return bookRepository.save(book);
}
    public long getAllBooksCount() {
        return bookRepository.count();
    }

    public Optional<BookModel> updateBook(Long id, BookModel bookDetails) {
        return bookRepository.findById(id)
        .map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setGenre(bookDetails.getGenre());
            book.setCoverImageUrl(bookDetails.getCoverImageUrl());
            book.setPublishedYear(bookDetails.getPublishedYear());
            book.setDescription(bookDetails.getDescription());
            return bookRepository.save(book);
        });
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

