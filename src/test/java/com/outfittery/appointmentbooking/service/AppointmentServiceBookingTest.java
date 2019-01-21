package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.exception.OperationNotAllowedException;
import com.outfittery.appointmentbooking.model.Appointment;
import com.outfittery.appointmentbooking.model.Customer;
import com.outfittery.appointmentbooking.model.Stylist;
import com.outfittery.appointmentbooking.repository.AppointmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AppointmentServiceBookingTest {

    @Autowired
    private AppointmentService appointmentService;

    @MockBean
    AppointmentRepository appointmentRepository;

    @MockBean
    StylistService stylistService;

    @MockBean
    CustomerService customerService;

    private Appointment appointment;
    private Customer customer;
    private Stylist stylist;

    @TestConfiguration
    static class AppointmentServiceImplTestContextConfiguration {

        @Bean
        public AppointmentService appointmentService() {
            return new AppointmentServiceImpl();
        }
    }

    @Before
    public void setup() throws NoResourceFoundException {
        customer = new Customer("testCustomer");
        customer.setId("1");

        Mockito.when(customerService.findCustomerById(customer.getId())).thenReturn(customer);

        stylist = new Stylist("testStylist");
        stylist.setId("1");

        Mockito.when(stylistService.findStylistById(stylist.getId())).thenReturn(stylist);

        appointment = new Appointment(
                        LocalDateTime.of(2019,1,20, 11, 30),
                        stylist,
                        customer);
        appointment.setId("1");

        Mockito.when(appointmentRepository.saveAndFlush(appointment)).thenReturn(appointment);

    }

    @Test
    public void givenAppointmentToService_whenCreateAndRetrieveAppointment_thenOK() throws NoResourceFoundException, OperationNotAllowedException {
        Appointment createdAppointment =
                appointmentService.createAppointment(appointment);

        assertNotNull(createdAppointment);
        assertEquals(customer.getName(), createdAppointment.getCustomer().getName());
        assertEquals(stylist.getName(), createdAppointment.getStylist().getName());
        assertEquals(createdAppointment.getSessionStart(), appointment.getSessionStart());
        assertEquals(createdAppointment.getId(), appointment.getId());
    }
}
