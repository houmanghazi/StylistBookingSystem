package com.outfittery.appointmentbooking.repository;

import com.outfittery.appointmentbooking.AppointmentBookingApplication;
import com.outfittery.appointmentbooking.model.Customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void givenCustomerRepository_whenSaveAndRetrieveEntity_thenOK() {
        Customer customer = customerRepository
                .save(new Customer("test"));
        Customer foundCustomer = customerRepository.findById(customer.getId()).get();

        assertNotNull(foundCustomer);
        assertEquals(customer.getName(), foundCustomer.getName());
        assertNotNull(customer.getId());
    }
}