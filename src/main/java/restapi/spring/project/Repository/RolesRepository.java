package restapi.spring.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restapi.spring.project.Model.RolesModel;

public interface RolesRepository extends JpaRepository<restapi.spring.project.Model.RolesModel, Long> {
    RolesModel findByRoleName(String roleName);
}