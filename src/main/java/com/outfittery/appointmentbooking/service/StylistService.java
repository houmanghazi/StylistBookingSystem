package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.model.Stylist;

import java.time.LocalDateTime;
import java.util.List;

public interface StylistService {
    /**
     * Retrieves a list of all registered stylists
     *
     * @return List of Stylist
     */
    public List<Stylist> retrieveAll();

    /**
     * Retrieves stylist by given Id
     *
     * @param id
     * @return Stylist
     */
    public Stylist findStylistById(String id);

    /**
     * Finds any stylist that do not have an existing booking at th time-slot
     *
     * @param startTime
     * @return Stylist
     * @throws NoResourceFoundException
     * Thrown when all stylists are busy at the given time-slot
     */
    public Stylist findAnAvailableStylistAtTimeSlot(LocalDateTime startTime) throws NoResourceFoundException;

    /**
     * Counts the registered stylists
     *
     * @return long
     */
    public long count();

    /**
     * Adds a new stylist to the system
     *
     * @param stylist
     * @return
     */
    public Stylist addStylist(Stylist stylist);
}
