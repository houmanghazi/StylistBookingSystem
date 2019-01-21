package com.outfittery.appointmentbooking.dto;

import java.util.HashSet;
import java.util.Set;

public class AvailableTimeSlotsResponse {

    private Set<String> availableTimeSlots;

    public AvailableTimeSlotsResponse() {
        this.availableTimeSlots = new HashSet<>();
    }

    public Set<String> getAvailableTimeSlots() {
        return this.availableTimeSlots;
    }

    public void setAvailableTimeSlots(Set<String> availableTimeSlots) {
        this.availableTimeSlots = availableTimeSlots;
    }
}
