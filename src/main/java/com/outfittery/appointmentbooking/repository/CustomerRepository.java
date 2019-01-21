package com.outfittery.appointmentbooking.repository;

import com.outfittery.appointmentbooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
