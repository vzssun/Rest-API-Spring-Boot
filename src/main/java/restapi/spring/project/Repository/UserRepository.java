package restapi.spring.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.spring.project.Model.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserModel, Long> {
    // You can add custom query methods here, for example:
    // List<User> findByLastName(String lastName);
    Optional<UserModel> findByEmail(String email);

    List<UserModel> findByExpiredAtBefore(Date expiration);


}

