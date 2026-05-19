package restapi.spring.project.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Management", description = "Endpoints restritos a MANAGER — operações administrativas")
@RestController
@RequestMapping("/api/management")
@PreAuthorize("hasRole('MANAGER')")
public class ManagementController {

    //
    @Operation(summary = "Leitura management")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @GetMapping
    @PreAuthorize("hasAuthority('management:read')")
    public String get() {
        return "GET:: ManagementController";
    }
    @Operation(summary = "Criação management")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @PostMapping
    @PreAuthorize("hasAuthority('management:create')")
    public String post() {
        return "POST:: ManagementController";
    }
    @Operation(summary = "Atualização management")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @PutMapping
    @PreAuthorize("hasAuthority('management:update')")
    public String put() {
        return "PUT:: ManagementController";
    }
    @Operation(summary = "Remoção management")
    @ApiResponse(responseCode = "200", description = "Acesso permitido")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @DeleteMapping
    @PreAuthorize("hasAuthority('management:delete')")
    public String delete() {
        return "DELETE:: ManagementController";
        }
}
