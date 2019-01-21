package com.outfittery.appointmentbooking.repository;

import com.outfittery.appointmentbooking.model.Appointment;
import com.outfittery.appointmentbooking.model.Customer;
import com.outfittery.appointmentbooking.model.Stylist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest()
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StylistRepository stylistRepository;

    private Customer customer;
    private Stylist stylist;

    @Before
    public void setup(){
         customer = customerRepository.save(new Customer("testCustomer"));
         stylist = stylistRepository.save(new Stylist("testStylist"));
    }

    @Test
    public void givenAppointmentRepository_whenSaveAndRetrieveEntity_thenOK() {
        Appointment appointment = appointmentRepository
                .save(new Appointment(LocalDateTime.of(2019,1,20, 11, 30), stylist, customer));
        Appointment foundAppointment = appointmentRepository.findById(appointment.getId()).get();

        assertNotNull(foundAppointment);
        assertEquals(customer.getName(), foundAppointment.getCustomer().getName());
        assertEquals(stylist.getName(), foundAppointment.getStylist().getName());
        assertNotNull(appointment.getId());
    }

    @Test
    public void whenSaveAppointment_thenfindAppointmentBetweenDates_thenOk() {
        Appointment appointment = appointmentRepository
                .save(new Appointment(LocalDateTime.of(2019,1,20, 11, 30), stylist, customer));
        List<Appointment> foundAppointments = appointmentRepository.findAppointmentsBySessionStartBetween(LocalDateTime.of(2019,1,20, 11, 0),
                LocalDateTime.of(2019,1,20, 13, 0));
        assertTrue(foundAppointments
                    .stream().anyMatch(a -> appointment.getId().equals(appointment.getId()))
        );
    }
}
