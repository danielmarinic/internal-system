package sk.itaps.portal.service;

import sk.itaps.portal.domain.jpa.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Customer findById(int id);
    public Customer saveOrUpdate(Customer customer);
    public void delete(int id);
    public void update(Customer customer, int id);
}
