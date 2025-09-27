package tech.miladsadeghi.accounts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.miladsadeghi.accounts.constants.AccountsConstants;
import tech.miladsadeghi.accounts.dtos.CustomerDTO;
import tech.miladsadeghi.accounts.dtos.ResponseDTO;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

}
