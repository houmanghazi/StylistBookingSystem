package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.model.Customer;
import com.outfittery.appointmentbooking.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerRepository customerRepository;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerById(String id) throws NoResourceFoundException {
        return customerRepository.findById(id).orElseThrow(()->new NoResourceFoundException("Customer with id '" + id + "' could not be found"));
    }

    @Override

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public boolean customerHasOverlappingAppointment(String customerId, LocalDateTime dateTime) {
        return customerRepository.findById(customerId).get()
                .getAppointmentSet()
                .stream()
                .anyMatch(appointment ->
                        (appointment.getSessionStart().equals(dateTime))
                );
    }
}
