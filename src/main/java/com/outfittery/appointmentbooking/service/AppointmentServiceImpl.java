package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.ApplicationException;
import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.exception.OperationNotAllowedException;
import com.outfittery.appointmentbooking.model.Appointment;
import com.outfittery.appointmentbooking.model.Customer;
import com.outfittery.appointmentbooking.model.Stylist;
import com.outfittery.appointmentbooking.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private AppointmentRepository appointmentRepository;
    private CustomerService customerService;
    private StylistService stylistService;

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setStylistService(StylistService stylistService) {
        this.stylistService = stylistService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Appointment createAppointment(Appointment appointment) throws NoResourceFoundException, OperationNotAllowedException {
        Customer customer = customerService.findCustomerById(appointment.getCustomer().getId());
        if(customerService.customerHasOverlappingAppointment(customer.getId(), appointment.getSessionStart()))
            throw new OperationNotAllowedException("Customer already has an appointment at the requested time-slot");

        appointment.setCustomer(customer);

        Stylist stylist = (appointment.getStylist() == null) ?
                        stylistService.findAnAvailableStylistAtTimeSlot(appointment.getSessionStart()) :
                        stylistService.findStylistById(appointment.getStylist().getId());

        appointment.setStylist(stylist);

        return appointmentRepository.saveAndFlush(appointment);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<Appointment> retrieveAllAppointmentsWithinPeriod(LocalDateTime from, LocalDateTime to) {
        return appointmentRepository.findAppointmentsBySessionStartBetween(from, to);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<Appointment> findAppointmentsAt(LocalDateTime sessionStartTime) {
        return appointmentRepository.findAppointmentsBySessionStartEquals(sessionStartTime);
    }


}