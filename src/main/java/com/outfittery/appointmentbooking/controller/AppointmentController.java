package com.outfittery.appointmentbooking.controller;

import com.outfittery.appointmentbooking.dto.AppointmentRequest;
import com.outfittery.appointmentbooking.exception.ApplicationException;
import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.exception.OperationNotAllowedException;
import com.outfittery.appointmentbooking.model.Appointment;
import com.outfittery.appointmentbooking.model.Customer;
import com.outfittery.appointmentbooking.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path="/api/appointments/")
@Api(value = "AppointmentsControllerApi", tags = {"appointments"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {
    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    private AppointmentService appointmentService;

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(path = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates an appointment for customer with one of the stylists at an available time-slot.", notes = "Creates an appointment for customer and stylist")
    public Appointment createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        Appointment appointment = new Appointment();

        Customer customer = new Customer();
        customer.setId(appointmentRequest.getCustomerId());
        appointment.setCustomer(customer);

        appointment.setSessionStart(appointmentRequest.getDateTime());
        try {
            appointment = appointmentService.createAppointment(appointment);
        } catch (NoResourceFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (OperationNotAllowedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return appointment;
    }
}