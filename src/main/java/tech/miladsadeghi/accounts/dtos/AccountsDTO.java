package tech.miladsadeghi.accounts.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDTO {

    @NotEmpty(message = "Account number must not be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Account number must be a 10-digit numeric value")
    private Long accountNumber;

    @NotEmpty(message = "Account type must not be empty")
    private String accountType;

    @NotEmpty(message = "Branch address must not be empty")
    private String branchAddress;
}
