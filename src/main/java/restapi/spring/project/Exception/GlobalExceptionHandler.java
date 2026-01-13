package restapi.spring.project.Exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import restapi.spring.project.Dto.response.ApiResponse;

import java.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.failure("Internal server error"));
    }

    @ExceptionHandler({BadCredentialsException.class,
            AccountExpiredException.class,
            AccessDeniedException.class,
            SignatureException.class,
            ExpiredJwtException.class})
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;
        switch (exception.getClass().getSimpleName()) {
            case "BadCredentialsException":
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("description", "The username or password is incorrect.");
                return errorDetail;
            case "AccountExpiredException":
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("description", "You are not authorized to access this resource");
                break;
            case "AccessDeniedException":
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "You are not authorized to access this resource");
                break;
            case "SignatureException":
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("description", "The JWT signature is invalid");
                break;
            case "ExpiredJwtException":
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception
                        .getMessage());
                errorDetail.setProperty("description", "The JWT token has expired");
                break;
        }
        return errorDetail;
    }
}

