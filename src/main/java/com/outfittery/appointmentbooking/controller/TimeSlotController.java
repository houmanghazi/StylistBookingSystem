package com.outfittery.appointmentbooking.controller;

import com.outfittery.appointmentbooking.dto.AvailableTimeSlotsResponse;
import com.outfittery.appointmentbooking.exception.TimeSlotRequestException;
import com.outfittery.appointmentbooking.service.TimeSlotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequestMapping(path="/api/timeslots/")
@Api(value = "TimeSlotsControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeSlotController {
    private static final Logger log = LoggerFactory.getLogger(TimeSlotController.class);

    private TimeSlotService timeSlotService;

    @Autowired
    public void setTimeSlotService(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping(path="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lists available time-slots", notes = "Returns a list of all available time-slots for which at least one stylist is available")
    public AvailableTimeSlotsResponse listTimeSlots(
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = ISO.DATE)
            @ApiParam(defaultValue = "Today", example = "yyyy-MM-dd")
                    LocalDate from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = ISO.DATE)
            @ApiParam(defaultValue = "Three months from today", example = "yyyy-MM-dd")
                    LocalDate to) {

        AvailableTimeSlotsResponse availableTimeSlotsResponse = new AvailableTimeSlotsResponse();

        try {
            availableTimeSlotsResponse.setAvailableTimeSlots(
                    timeSlotService.retrieveAllAvailableTimeSlots(from, to)
                            .stream()
                            .map(e -> e.getStartTimeString())
                            .collect(Collectors.toCollection(TreeSet::new))
            );
        } catch (TimeSlotRequestException e) {
                throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, e.getMessage());
        }

        return availableTimeSlotsResponse;
    }
}
