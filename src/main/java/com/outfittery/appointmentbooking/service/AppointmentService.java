package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.ApplicationException;
import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.exception.OperationNotAllowedException;
import com.outfittery.appointmentbooking.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    /**
     * Creates an appointment for customer at requested date/time
     *
     * @param appointment
     * @return created appointment
     * @throws NoResourceFoundException
     * thrown when either the customer with requested ID is not found or no stylist is available for the time-slot
     * @throws OperationNotAllowedException
     * thrown when the customer already has a booking at the time-slot
     */
    public Appointment createAppointment(Appointment appointment) throws NoResourceFoundException, OperationNotAllowedException;

    /**
     * Retrieves all appointments within the period
     *
     * @param from
     * @param to
     * @return List of Appointment
     */
    public List<Appointment> retrieveAllAppointmentsWithinPeriod(LocalDateTime from, LocalDateTime to);

    /**
     * Retrieves all appointments at a given time-slot
     *
     * @param sessionStartTime
     * @return List of Appointment
     */
    public List<Appointment> findAppointmentsAt(LocalDateTime sessionStartTime);
}
