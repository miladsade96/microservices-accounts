package tech.miladsadeghi.accounts.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @NotEmpty(message = "Email must not be empty")
    @Email(regexp = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$", message = "Email must be a valid email address")
    private String email;

    @Pattern(regexp = "^(\\+98|0)?9\\d{9}$", message = "Mobile number must be a valid mobile number")
    private String mobileNumber;

    private AccountsDTO accountsDTO;
}
