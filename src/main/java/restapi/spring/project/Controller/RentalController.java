package restapi.spring.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import restapi.spring.project.Services.RentalService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;

import restapi.spring.project.Dto.response.ApiResponse;
import restapi.spring.project.Model.RentalModel;

import org.springframework.http.HttpHeaders;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Rentals", description = "Gerenciamento de aluguéis — dados transacionais, sem cache")
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    
    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Lista aluguéis — todos ou filtrados por usuário")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Aluguéis retornados")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Nenhum aluguel encontrado")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nenhum aluguel para o usuário informado")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RentalModel>>> getRentals(
        @RequestParam(value = "userId", required = false) Long userId) {

        List<RentalModel> rentals;

        if (userId != null) {
            rentals = rentalService.getRentalsByUserId(userId);

        if (rentals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(
                    "No rentals found for user ID: " + userId
                ));
        }

        return ResponseEntity.ok(
            ApiResponse.success(
                "Rentals fetched for user ID: " + userId,
                rentals
            )
        );
    }

    rentals = rentalService.getAllRentals();

    if (rentals.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.failure("No rentals available"));
    }

    return ResponseEntity.ok(
        ApiResponse.success("All rentals fetched", rentals)
    );
}

    @Operation(summary = "Cria um novo aluguel")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Aluguel criado com sucesso")
    @PostMapping
    public ResponseEntity<RentalModel> createRental(@RequestBody RentalModel entity) {
        RentalModel saved = rentalService.saveRental(entity);
        return ResponseEntity
                .status(HttpStatus.CREATED) // Another example, we can use HttpStatus.CREATED instead of .created()
                .header(HttpHeaders.LOCATION, "/api/rentals/" + saved.getId())
                .body(saved);
    }

    @Operation(summary = "Atualiza um aluguel existente")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Aluguel atualizado")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Aluguel não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<RentalModel> updateRental(
        @PathVariable Long id,
        @RequestBody RentalModel entity) {

    entity.setId(id); // enforce path ID
    RentalModel updatedRental = rentalService.saveRental(entity);
    if (updatedRental == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(updatedRental);
    }

    @Operation(summary = "Remove um aluguel")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Aluguel removido com sucesso")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Aluguel não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        boolean deleted = rentalService.deleteRentalById(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content (Basically noContent gives us status codes)
        } else {
            return ResponseEntity.notFound().build();  // 404 if not found
            }
    }
}

