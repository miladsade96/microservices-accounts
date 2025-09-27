package tech.miladsadeghi.accounts.services;

import tech.miladsadeghi.accounts.dtos.CustomerDTO;

public interface IAccountsService {
    
    void createAccount(CustomerDTO customerDTO);
}
