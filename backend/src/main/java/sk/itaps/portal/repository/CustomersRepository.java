package sk.itaps.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.itaps.portal.domain.jpa.Customer;

import java.util.List;

public interface CustomersRepository extends JpaRepository<Customer, Integer> {
    public List<Customer> findCustomersByRegistration(String registration);
}
