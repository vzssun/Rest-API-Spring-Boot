package restapi.spring.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.spring.project.Model.UserModel;

public interface UserRepository  extends JpaRepository<UserModel, Long> {
    // You can add custom query methods here, for example:
    // List<User> findByLastName(String lastName);
}