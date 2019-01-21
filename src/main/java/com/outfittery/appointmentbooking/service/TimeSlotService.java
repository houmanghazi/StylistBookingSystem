package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.TimeSlotRequestException;
import com.outfittery.appointmentbooking.pojos.TimeSlot;

import java.time.LocalDate;
import java.util.Set;

public interface TimeSlotService {

    /**
     * Retrieves a list of all available time-slots within the requested period
     *
     * @param from
     * start date of the period
     * if not set or is before current date, it will be replaced by current date
     * @param to
     * end date of the period
     * @return Set of TimeSlot
     * @throws TimeSlotRequestException
     * Thrown if end date of the period is before current date
     */
    public Set<TimeSlot> retrieveAllAvailableTimeSlots(LocalDate from, LocalDate to) throws TimeSlotRequestException;
}
