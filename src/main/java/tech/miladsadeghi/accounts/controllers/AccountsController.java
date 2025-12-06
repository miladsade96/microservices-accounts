package tech.miladsadeghi.accounts.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.miladsadeghi.accounts.constants.AccountsConstants;
import tech.miladsadeghi.accounts.dtos.AccountsContactInfoDTO;
import tech.miladsadeghi.accounts.dtos.CustomerDTO;
import tech.miladsadeghi.accounts.dtos.ErrorResponseDTO;
import tech.miladsadeghi.accounts.dtos.ResponseDTO;
import tech.miladsadeghi.accounts.services.IAccountsService;

@Tag(
        name = "Accounts",
        description = "APIs for managing customer accounts"
)
@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);

    private final IAccountsService iAccountsService;
    private final String buildVersion;
    private final Environment environment;
    private final AccountsContactInfoDTO accountsContactInfoDTO;

    public AccountsController(
            IAccountsService iAccountsService,
            @Value("${build.version}") String buildVersion,
            Environment environment,
            AccountsContactInfoDTO accountsContactInfoDTO) {

        this.iAccountsService = iAccountsService;
        this.buildVersion = buildVersion;
        this.environment = environment;
        this.accountsContactInfoDTO = accountsContactInfoDTO;
    }

    @Operation(
            summary = "Create Account",
            description = "Create a new account for a customer"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Account created successfully"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        iAccountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account Details",
            description = "Fetch account details for a customer by mobile number"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Account details fetched successfully"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @GetMapping(path = "/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(
            @RequestParam
            @Pattern(
                    regexp = "^(\\+98|0)?9\\d{9}$",
                    message = "Mobile number must be a valid mobile number"
            ) String mobileNumber) {
        CustomerDTO customerDTO = iAccountsService.fetchAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @Operation(
            summary = "Update Account Details",
            description = "Update account details for a customer"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Account details updated successfully"),
                    @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO) {
        boolean isUpdated = iAccountsService.updateAccount(customerDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Delete Account",
            description = "Delete a customer's account by mobile number"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
                    @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(
            @RequestParam
            @Pattern(
                    regexp = "^(\\+98|0)?9\\d{9}$",
                    message = "Mobile number must be a valid mobile number"
            ) String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Get Build Version",
            description = "Retrieve the current build version of the application"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Build version fetched successfully"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @GetMapping("/build-version")
    @Retry(name = "getBuildVersionRetry", fallbackMethod = "getBuildVersionFallback")
    public ResponseEntity<String> getBuildVersion() {
        LOGGER.info("Invoking getBuildVersion of AccountsController");
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String> getBuildVersionFallback(Throwable throwable) {
        LOGGER.info("Returning fallback Build Version");
        return ResponseEntity.status(HttpStatus.OK).body("0.0.0-UNKNOWN");
    }

    @Operation(
            summary = "Get Java Version",
            description = "Retrieve the current Java version the application is running on"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Java version fetched successfully"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @GetMapping("/java-version")
    @RateLimiter(name = "getJavaVersion")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Retrieve the contact information from environment variables"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Java version fetched successfully"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            ))
            }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDTO> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDTO);
    }
}
