package com.outfittery.appointmentbooking.repository;

import com.outfittery.appointmentbooking.model.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StylistRepository extends JpaRepository<Stylist, String> {
}
