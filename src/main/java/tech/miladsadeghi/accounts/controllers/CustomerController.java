package tech.miladsadeghi.accounts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
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
            @RequestHeader("ms-correlation-id") String correlationId,
            @RequestParam
            @Pattern(
                    regexp = "^(\\+98|0)?9\\d{9}$",
                    message = "Mobile number must be a valid mobile number"
            ) String mobileNumber) {

        LOG.info("Received request to fetch customer details for mobile number: {} with correlation ID: {}",
                mobileNumber, correlationId);
        CustomerDetailsDTO customerDetailsDTO = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.ok(customerDetailsDTO);
    }
}
