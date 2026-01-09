package restapi.spring.project.Repository;
import restapi.spring.project.Model.ReservationModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {}