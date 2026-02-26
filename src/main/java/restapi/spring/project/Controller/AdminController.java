package restapi.spring.project.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class AdminController {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read, management:read')")
    public String get() {
        return "GET:: AdminController";
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create, management:create')")
    public String post() {
        return "POST:: AdminController";
    }
    @PutMapping
    @PreAuthorize("hasAnyAuthority('admin:update, management:update')")
    public String put() {
        return "PUT:: AdminController";
    }
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('admin:delete, management:delete')")
    public String delete() {
        return "DELETE:: AdminController";
        }
}
