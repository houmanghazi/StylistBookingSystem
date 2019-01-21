package com.outfittery.appointmentbooking.repository;

import com.outfittery.appointmentbooking.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    public List<Appointment> findAppointmentsBySessionStartBetween(LocalDateTime from, LocalDateTime to);
    public List<Appointment> findAppointmentsBySessionStartEquals(LocalDateTime startTime);
}
