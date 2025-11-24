package tech.miladsadeghi.accounts.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.miladsadeghi.accounts.dtos.AccountsDTO;
import tech.miladsadeghi.accounts.dtos.CardsDTO;
import tech.miladsadeghi.accounts.dtos.CustomerDetailsDTO;
import tech.miladsadeghi.accounts.dtos.LoansDTO;
import tech.miladsadeghi.accounts.entities.Accounts;
import tech.miladsadeghi.accounts.entities.Customer;
import tech.miladsadeghi.accounts.exceptions.ResourceNotFoundException;
import tech.miladsadeghi.accounts.mappers.AccountsMapper;
import tech.miladsadeghi.accounts.mappers.CustomerMapper;
import tech.miladsadeghi.accounts.repositories.AccountsRepository;
import tech.miladsadeghi.accounts.repositories.CustomerRepository;
import tech.miladsadeghi.accounts.services.ICustomerService;
import tech.miladsadeghi.accounts.services.client.CardsFeignClient;
import tech.miladsadeghi.accounts.services.client.LoansFeignClient;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", String.valueOf(customer.getCustomerId())));

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDTO(customer, new CustomerDetailsDTO());
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(account, new AccountsDTO()));

        ResponseEntity<LoansDTO> loansDTOResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDTO.setLoansDTO(loansDTOResponseEntity.getBody());

        ResponseEntity<CardsDTO> cardsDTOResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());

        return customerDetailsDTO;
    }
}
