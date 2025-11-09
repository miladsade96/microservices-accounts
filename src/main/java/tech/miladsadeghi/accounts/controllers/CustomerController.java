package tech.miladsadeghi.accounts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.miladsadeghi.accounts.dtos.CustomerDetailsDTO;
import tech.miladsadeghi.accounts.dtos.ErrorResponseDTO;
import tech.miladsadeghi.accounts.services.ICustomerService;

@Tag(
        name = "Rest API for Customers",
        description = "APIs for managing customers details"
)
@RestController
@RequestMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@AllArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @Operation(
            summary = "Fetch Customer Details",
            description = "Fetch customer details by mobile number"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Customer details fetched successfully"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(
            @RequestParam
            @Pattern(
                    regexp = "^(\\+98|0)?9\\d{9}$",
                    message = "Mobile number must be a valid mobile number"
            ) String mobileNumber) {

        CustomerDetailsDTO customerDetailsDTO = customerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.ok(customerDetailsDTO);
    }
}
