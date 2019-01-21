package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.model.Customer;

import java.time.LocalDateTime;

public interface CustomerService {
    /**
     * Finds customer by Id
     *
     * @param id
     * @return customer
     * @throws NoResourceFoundException
     */
    public Customer findCustomerById(String id) throws NoResourceFoundException;

    /**
     * Creates new customer in system
     *
     * @param customer
     * @return created customer
     */
    public Customer addCustomer(Customer customer);

    /**
     * Checks whether customer already has an appointment at the given time
     *
     * @param customerId
     * @param dateTime
     * @return boolean
     */
    public boolean customerHasOverlappingAppointment(String customerId, LocalDateTime dateTime);
}
