package sk.itaps.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.itaps.portal.domain.jpa.Customer;
import sk.itaps.portal.repository.AddressRepository;
import sk.itaps.portal.repository.CustomersRepository;
import sk.itaps.portal.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        customersRepository.findAll().forEach(customer1 -> customers.add(customer1));
        return customers;
    }

    public Customer findById(int id) {
        return customersRepository.findById(id).get();
    }

    public Customer saveOrUpdate(Customer customer) {
        addressRepository.save(customer.getAddress());
        return customersRepository.save(customer);
    }

    public void delete(int id) {
        customersRepository.deleteById(id);
    }

    public void update(Customer customer, int id) {
        customersRepository.save(customer);
    }
}
