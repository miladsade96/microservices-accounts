package tech.miladsadeghi.accounts.services;

import tech.miladsadeghi.accounts.dtos.CustomerDTO;

public interface IAccountsService {

    void createAccount(CustomerDTO customerDTO);

    CustomerDTO fetchAccountDetails(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO);
}
