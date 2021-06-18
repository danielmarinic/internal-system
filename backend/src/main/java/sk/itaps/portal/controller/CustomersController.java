package sk.itaps.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sk.itaps.portal.domain.dto.CustomerDefaultDto;
import sk.itaps.portal.domain.dto.CustomerDto;
import sk.itaps.portal.domain.jpa.Customer;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.service.CustomerService;
import sk.itaps.portal.service.impl.OrsrServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomersController {
    @Autowired
    CustomerService customerService;

    @Autowired
    OrsrServiceImpl orsrService;

    @Autowired
    Mapper mapper;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(mapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/list")
    public List<CustomerDefaultDto> getCustomersList() {
        return customerService.getAllCustomers()
                .stream()
                .map(mapper::customerToCustomerDefaultDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer saveCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.saveOrUpdate(mapper.customerDtoToCustomer(customerDto));
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") int id) {
        return customerService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") int id) {
        customerService.delete(id);
    }

    @PutMapping
    public Customer updateCustomer(@PathVariable("id") int id, @RequestBody CustomerDto customer) {
        Customer newCustomer = mapper.customerDtoToCustomer(customer);
        newCustomer.setId(id);
        return customerService.saveOrUpdate(newCustomer);
    }

    @GetMapping("/load/{ico}")
    @ResponseBody
    public CustomerDto loadCustomer(@PathVariable String ico) {
        return mapper.orsrDtoToCustomerDto(orsrService.getCompany(ico)[0]);
    }
}
