package tech.miladsadeghi.accounts.services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.miladsadeghi.accounts.dtos.LoansDTO;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/loans/fetch", consumes = "application/json")
    public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestParam String mobileNumber);

}
