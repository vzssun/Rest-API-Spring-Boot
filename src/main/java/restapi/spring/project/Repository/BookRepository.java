package restapi.spring.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import restapi.spring.project.Model.BookModel;

import java.util.List;
@EnableJpaRepositories
public interface BookRepository extends JpaRepository<BookModel, Long> {

    // You can add custom query methods here, for example:
    // List<BookModel> findByAuthor(String author);
    // boolean findByAvailable(Boolean available);

    List<BookModel> findByReservationId(Long reservationId);
//    List<BookModel> findBookbyTitle(String title);
}
