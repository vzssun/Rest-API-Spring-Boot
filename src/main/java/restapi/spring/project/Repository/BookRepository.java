package restapi.spring.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import restapi.spring.project.Model.BookModel;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    // You can add custom query methods here, for example:
    // List<BookModel> findByAuthor(String author);
}
