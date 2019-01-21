package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.TimeSlotRequestException;
import com.outfittery.appointmentbooking.util.TimeSlotUtil;
import com.outfittery.appointmentbooking.model.Appointment;
import com.outfittery.appointmentbooking.pojos.TimeSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TimeSlotServiceImpl implements TimeSlotService{
    private static final Logger log = LoggerFactory.getLogger(TimeSlotServiceImpl.class);

    private StylistService stylistService;

    private AppointmentService appointmentService;

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Autowired
    public void setStylistService(StylistService stylistService) {
        this.stylistService = stylistService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public Set<TimeSlot> retrieveAllAvailableTimeSlots(LocalDate from, LocalDate to) throws TimeSlotRequestException {

        from = (from != null && !from.isBefore(LocalDate.now()))? from : LocalDate.now();
        to = (to != null)? to : from.plusMonths(3);

        if (to.isBefore(from))
            throw new TimeSlotRequestException("the 'to' date must be after the 'from' date");

        long noOfStylists = stylistService.count();

        if (noOfStylists == 0)
            return new HashSet<>();
        List<TimeSlot> allTimeslotsWithinPeriod = TimeSlotUtil.generateAllTimeSlotsWithinPeriod(from, to);

        List<LocalDateTime> timeSlotsTaken =
                appointmentService.retrieveAllAppointmentsWithinPeriod(from.atStartOfDay(), to.plusDays(1).atStartOfDay())
                        .stream()
                        .collect(Collectors.groupingBy(Appointment::getSessionStart, Collectors.counting()))
                        .entrySet().stream()
                        .filter(e -> e.getValue() >= noOfStylists)
                        .map(e -> e.getKey())
                        .collect(Collectors.toList());

        return allTimeslotsWithinPeriod
                .stream()
                .filter(timeSlot -> !timeSlotsTaken.contains(timeSlot.getSessionStartTime()))
                .collect(Collectors.toSet());
    }


}
