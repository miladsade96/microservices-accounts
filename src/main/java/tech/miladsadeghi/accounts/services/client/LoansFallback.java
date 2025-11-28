package tech.miladsadeghi.accounts.services.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tech.miladsadeghi.accounts.dtos.LoansDTO;

@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDTO> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
