package tech.miladsadeghi.accounts.services;

import tech.miladsadeghi.accounts.dtos.CustomerDetailsDTO;

public interface ICustomerService {

    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId);
}
