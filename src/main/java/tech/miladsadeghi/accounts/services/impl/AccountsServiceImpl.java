package tech.miladsadeghi.accounts.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.miladsadeghi.accounts.constants.AccountsConstants;
import tech.miladsadeghi.accounts.dtos.CustomerDTO;
import tech.miladsadeghi.accounts.entities.Accounts;
import tech.miladsadeghi.accounts.entities.Customer;
import tech.miladsadeghi.accounts.exceptions.CustomerAlreadyExistsException;
import tech.miladsadeghi.accounts.mappers.CustomerMapper;
import tech.miladsadeghi.accounts.repositories.AccountsRepository;
import tech.miladsadeghi.accounts.repositories.CustomerRepository;
import tech.miladsadeghi.accounts.services.IAccountsService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            String message = String.format("Customer with mobile number %s already exists!", customerDTO.getMobileNumber());
            throw new CustomerAlreadyExistsException(message);
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Anonymous");
        return account;
    }
}
