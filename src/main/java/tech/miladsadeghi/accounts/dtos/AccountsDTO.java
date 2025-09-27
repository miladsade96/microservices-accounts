package tech.miladsadeghi.accounts.dtos;

import lombok.Data;

@Data
public class AccountsDTO {

    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
