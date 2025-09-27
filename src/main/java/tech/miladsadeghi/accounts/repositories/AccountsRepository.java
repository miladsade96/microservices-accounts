package tech.miladsadeghi.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.miladsadeghi.accounts.entities.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
}
