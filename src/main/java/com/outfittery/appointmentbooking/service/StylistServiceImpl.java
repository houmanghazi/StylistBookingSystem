package com.outfittery.appointmentbooking.service;

import com.outfittery.appointmentbooking.exception.NoResourceFoundException;
import com.outfittery.appointmentbooking.model.Appointment;
import com.outfittery.appointmentbooking.model.Stylist;
import com.outfittery.appointmentbooking.repository.StylistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StylistServiceImpl implements StylistService {
    private static final Logger log = LoggerFactory.getLogger(StylistServiceImpl.class);

    private StylistRepository stylistRepository;

    private AppointmentService appointmentService;

    @Autowired
    public void setStylistRepository(StylistRepository stylistRepository) {
        this.stylistRepository = stylistRepository;
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public List<Stylist> retrieveAll(){
        return stylistRepository.findAll();
    }

    @Override
    public Stylist findStylistById(String id) {
        return stylistRepository.getOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Stylist findAnAvailableStylistAtTimeSlot(LocalDateTime startTime) throws NoResourceFoundException {
        List<Appointment> appointments = appointmentService.findAppointmentsAt(startTime);
        Stylist firstAvailableStylist =
                stylistRepository.findAll()
                        .stream()
                        .filter(stylist->
                            !appointments.stream()
                                    .anyMatch(appointment -> appointment.getStylist().getId().equals(stylist.getId()))
                            )
                        .findFirst()
                        .orElseThrow(() -> new NoResourceFoundException("No available stylist found at specified time"));
        return firstAvailableStylist;
    }

    @Override
    public long count() {
        return stylistRepository.count();
    }

    @Override
    public Stylist addStylist(Stylist stylist) {
        return stylistRepository.save(stylist);
    }
}
