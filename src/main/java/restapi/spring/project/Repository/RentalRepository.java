package restapi.spring.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.spring.project.Model.RentalModel;

import java.util.List;

public interface RentalRepository extends JpaRepository<RentalModel, Long> {
    // Custom query to find rentals by userId
    List<RentalModel> findByUserId(Long userId);
}
