package com.outfittery.appointmentbooking.util;

import com.outfittery.appointmentbooking.exception.TimeSlotRequestException;
import com.outfittery.appointmentbooking.pojos.TimeSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class TimeSlotUtil {

    private static final Logger log = LoggerFactory.getLogger(TimeSlotUtil.class);

    protected static final LocalTime START_TIME_OF_WOK_DAY = LocalTime.of(9, 0);
    protected static final Duration DAILY_WORK_DURATION = Duration.ofHours(8);
    protected static final LocalTime END_TIME_OF_WORK_DAY = START_TIME_OF_WOK_DAY.plus(DAILY_WORK_DURATION);

    public static final Duration SESSION_DURATION = Duration.ofMinutes(30);

    /**
     * Generates all possible time-slots between the dates given
     *
     * @param from
     * Date of the beginning of the period
     * @param to
     * Date of the end of the period
     * @return
     * List of all potential time-slots within the period
     */
    public static List<TimeSlot> generateAllTimeSlotsWithinPeriod(LocalDate from, LocalDate to) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        do {
            LocalTime current = START_TIME_OF_WOK_DAY;
            do {
                LocalDateTime sessionStart = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), current.getHour(), current.getMinute());
                timeSlots.add(generateTimeSlotAt(sessionStart));

                current = current.plus(SESSION_DURATION);
            } while(!current.equals(END_TIME_OF_WORK_DAY));

            from = from.plusDays(1);
        }
        while(!from.isAfter(to));

        return timeSlots;
    }

    /**
     * Checks whether given time-slot is within the stylists' working hours and duration is as expected
     * @param timeSlot
     * @throws TimeSlotRequestException
     * Thrown if the given time-slot does not meet the system requirements
     */
    public static void validateTimeSlot(TimeSlot timeSlot) throws TimeSlotRequestException {
        LocalTime timeSlotStart = LocalTime.of(timeSlot.getSessionStartTime().getHour(), timeSlot.getSessionStartTime().getMinute());
        LocalTime timeSlotEnd = LocalTime.of(timeSlot.getSessionEndTime().getHour(), timeSlot.getSessionEndTime().getMinute());
        if (timeSlotStart.isBefore(START_TIME_OF_WOK_DAY)
                || timeSlotEnd.isAfter(END_TIME_OF_WORK_DAY)
                || !timeSlot.getDuration().equals(SESSION_DURATION))
            throw new TimeSlotRequestException("Requested time-slot does not fall within the stylists' working hours");
    }

    /**
     * Creates a valid time-slot at the given date/time
     * @param dateTime
     * @return TimeSlot
     * @throws TimeSlotRequestException
     * Thrown if a valid time-slot cannot be created at given time
     */
    public static TimeSlot getValidTimeSlotAt(LocalDateTime dateTime) throws TimeSlotRequestException {
        TimeSlot timeSlot = generateTimeSlotAt(dateTime);
        validateTimeSlot(timeSlot);
        return timeSlot;
    }

    private static TimeSlot generateTimeSlotAt(LocalDateTime dateTime) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDuration(SESSION_DURATION);
        LocalDateTime sessionStart = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute());
        timeSlot.setSessionStartTime(sessionStart);
        return timeSlot;
    }
}
