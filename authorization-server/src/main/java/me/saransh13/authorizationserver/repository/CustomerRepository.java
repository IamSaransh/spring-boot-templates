package me.saransh13.authorizationserver.repository;

import me.saransh13.authorizationserver.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    public  Customer findCustomerByEmail(String email);


}
