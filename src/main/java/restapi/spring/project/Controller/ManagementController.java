package restapi.spring.project.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management")
@PreAuthorize("hasRole('MANAGER')")
public class ManagementController {

    //

    @GetMapping
    @PreAuthorize("hasAuthority('management:read')")
    public String get() {
        return "GET:: ManagementController";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('management:create')")
    public String post() {
        return "POST:: ManagementController";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('management:update')")
    public String put() {
        return "PUT:: ManagementController";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('management:delete')")
    public String delete() {
        return "DELETE:: ManagementController";
        }
}
