package tech.miladsadeghi.accounts.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.miladsadeghi.accounts.dtos.CustomerDTO;
import tech.miladsadeghi.accounts.repositories.AccountsRepository;
import tech.miladsadeghi.accounts.repositories.CustomerRepository;
import tech.miladsadeghi.accounts.services.IAccountsService;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {

    }
}
