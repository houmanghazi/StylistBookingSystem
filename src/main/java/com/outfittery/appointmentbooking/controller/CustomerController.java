package com.outfittery.appointmentbooking.controller;

import com.outfittery.appointmentbooking.dto.CustomerRequest;
import com.outfittery.appointmentbooking.model.Customer;
import com.outfittery.appointmentbooking.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/customers/")
@Api(value = "CustomersControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path="/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Adds a new Customer", notes = "Creates a new Customer")
    public Customer createCustomer(@RequestBody CustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        return customerService.addCustomer(customer);
    }
}
