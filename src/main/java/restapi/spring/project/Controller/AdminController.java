package restapi.spring.project.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin", description = "Endpoints restritos a ADMIN — acesso total ao sistema")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class AdminController {

    @Operation(summary = "Leitura admin")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read, management:read')")
    public String get() {
        return "GET:: AdminController";
    }
    @Operation(summary = "Criação admin")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create, management:create')")
    public String post() {
        return "POST:: AdminController";
    }
    @Operation(summary = "Atualização admin")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('admin:update, management:update')")
    public String put() {
        return "PUT:: AdminController";
    }
    @Operation(summary = "Remoção admin")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('admin:delete, management:delete')")
    public String delete() {
        return "DELETE:: AdminController";
        }
}
