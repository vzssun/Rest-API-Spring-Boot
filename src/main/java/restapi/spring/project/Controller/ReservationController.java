package restapi.spring.project.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import restapi.spring.project.Dto.response.BookReservationResponse;
import restapi.spring.project.Dto.request.BookReservationRequest;
import restapi.spring.project.Dto.response.ApiResponse;
import restapi.spring.project.Model.ReservationModel;
import restapi.spring.project.Services.ReservationService;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

import java.util.List;

@Tag(name = "Reservations", description = "Gerenciamento de reservas — dados transacionais, sem cache")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // Get all reservations
    @Operation(summary = "Lista todas as reservas")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reservas retornadas com sucesso")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookReservationResponse>>> getAllReservations() {

        List<ReservationModel> reservations = reservationService.getAllReservations();

        // Convert each reservation to DTO
        List<BookReservationResponse> responseList = reservations.stream()
                .map(BookReservationResponse::from)
                .collect(Collectors.toList());

        ApiResponse<List<BookReservationResponse>> response =
                ApiResponse.success("All reservations fetched successfully", responseList);

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Cria uma reserva para um usuário")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Reserva criada com sucesso")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @PostMapping("/users/{userId}/reserve")
public ResponseEntity<ApiResponse<ReservationModel>> reserveBooks(
        @PathVariable Long userId,
        @RequestBody BookReservationRequest request) {

    ReservationModel reservation = reservationService.reserveBook(userId, request);

    return ResponseEntity.status(201)
            .body(ApiResponse.success("Reservation created successfully", reservation));
}

    // Get a single reservation by ID
    @Operation(summary = "Busca uma reserva por ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reserva encontrada")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<BookReservationResponse>> getReservationById(
            @PathVariable Long reservationId) {

        ReservationModel reservation = reservationService.getReservationById(reservationId);

        BookReservationResponse responseDto = BookReservationResponse.from(reservation);

        ApiResponse<BookReservationResponse> response =
                ApiResponse.success("Reservation fetched successfully", responseDto);

        return ResponseEntity.ok(response);
    }
}
