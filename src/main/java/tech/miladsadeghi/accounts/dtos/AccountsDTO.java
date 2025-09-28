package tech.miladsadeghi.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold account data"
)
public class AccountsDTO {

    @NotEmpty(message = "Account number must not be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Account number must be a 10-digit numeric value")
    @Schema(description = "Account number", example = "8293648592")
    private Long accountNumber;

    @NotEmpty(message = "Account type must not be empty")
    @Schema(description = "Type of the account", example = "SAVINGS")
    private String accountType;

    @NotEmpty(message = "Branch address must not be empty")
    @Schema(description = "Branch address", example = "123-Main St, City, Country")
    private String branchAddress;
}
