package tech.miladsadeghi.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response data"
)
@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    @Schema(description = "API path where the error occurred", example = "/api/v1/customers/123")
    private String apiPath;

    @Schema(description = "HTTP status code of the error", example = "404")
    private HttpStatus errorCode;

    @Schema(description = "Detailed error message", example = "Customer not found")
    private String errorMessage;

    @Schema(description = "Timestamp when the error occurred", example = "2024-06-15T14:30:00")
    private LocalDateTime errorTime;
}
