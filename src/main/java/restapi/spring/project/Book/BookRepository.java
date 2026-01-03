package restapi.spring.project.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    // You can add custom query methods here, for example:
    // List<BookModel> findByAuthor(String author);
}
