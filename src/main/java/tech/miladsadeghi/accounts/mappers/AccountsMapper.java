package tech.miladsadeghi.accounts.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tech.miladsadeghi.accounts.dtos.AccountsDTO;
import tech.miladsadeghi.accounts.entities.Accounts;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountsMapper {

    public static AccountsDTO mapToAccountsDTO(Accounts accounts, AccountsDTO accountsDTO) {
        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());
        return accountsDTO;
    }

    public static Accounts mapToAccounts(AccountsDTO accountsDTO, Accounts accounts) {
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;
    }
}
