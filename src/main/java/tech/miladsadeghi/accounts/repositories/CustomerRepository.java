package tech.miladsadeghi.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.miladsadeghi.accounts.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
