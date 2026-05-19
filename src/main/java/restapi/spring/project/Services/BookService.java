package restapi.spring.project.Services;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import restapi.spring.project.Dto.BookDTO;
import restapi.spring.project.Dto.response.PaginatedResponse;
import restapi.spring.project.Mapper.BookMapper;
import restapi.spring.project.Model.BookModel;
import restapi.spring.project.Model.ReservationModel;
import restapi.spring.project.Repository.BookRepository;
import restapi.spring.project.Repository.ReservationRepository;
import restapi.spring.project.Specification.BookSpecification;

@Service // Treat this class as a Service bean (can be injected into other components)
public class BookService {

    @Autowired // Inject an instance of BookRepository here
    private BookRepository bookRepository;

    @Autowired
        private BookMapper bookMapper; 
        
    @Autowired 
    private ReservationRepository reservationRepository;
  

    
    
    
    
    // offset pagination
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
    
    @Cacheable(value = "catalogo", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
      public PaginatedResponse<BookDTO> getAllBooks(Pageable pageable){
        Page<BookModel> page = bookRepository.findAll(pageable);
        return buildPaginatedResponse(page.map(bookMapper::toDTO));
    }
    @Cacheable(value = "catalogo", key = "#category + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginatedResponse<BookDTO> getBooksByCategory(Pageable pageable, String category){
        Specification<BookModel> spec = BookSpecification.withCategory(category);
        Page<BookModel> page = bookRepository.findAll(spec, pageable);
    
        return buildPaginatedResponse(page.map(bookMapper::toDTO));
    }

    @Cacheable(value = "catalogo", key = "#search + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginatedResponse<BookDTO> getBooksByName(Pageable pageable, String search){
        Specification<BookModel> spec = BookSpecification.withSearch(search);
        Page<BookModel> page = bookRepository.findAll(spec, pageable);
    
        return buildPaginatedResponse(page.map(bookMapper::toDTO));
    }
    @Cacheable(value = "catalogo", key = "#search + '-' + #category + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginatedResponse<BookDTO> getBooksByNameAndCategory(Pageable pageable, String search, String category){
        Specification<BookModel> spec = BookSpecification.withFilters(search, category);
        Page<BookModel> page = bookRepository.findAll(spec, pageable);
    
        return buildPaginatedResponse(page.map(bookMapper::toDTO));
    }
    @Cacheable(value = "disponibilidade", key = "#available + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginatedResponse<BookDTO> getBooksByAvailability(Pageable pageable, boolean available){
        Specification<BookModel> spec = BookSpecification.withAvailability(available);
        Page<BookModel> page = bookRepository.findAll(spec, pageable);
    
        return buildPaginatedResponse(page.map(bookMapper::toDTO));
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
    
    @CacheEvict(value = {"catalogo", "disponibilidade"}, allEntries = true)
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

    @CacheEvict(value = {"catalogo", "disponibilidade"}, allEntries = true)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookModel> getAllBookReservations(Long reservationId) {
        return bookRepository.findByReservationId(reservationId);
    }

        }

