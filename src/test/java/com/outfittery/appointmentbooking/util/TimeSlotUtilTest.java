package com.outfittery.appointmentbooking.util;

import com.outfittery.appointmentbooking.exception.TimeSlotRequestException;
import com.outfittery.appointmentbooking.pojos.TimeSlot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TimeSlotUtilTest {

    @Before
    public void setup() {
    }

    @After
    public void demolish() {
    }

    @Test
    public void testGenerateAllTimeSlotsWithinPeriod(){
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(10);
        long expectedSizeOfReturn = 11 * TimeSlotUtil.DAILY_WORK_DURATION.toMinutes() / TimeSlotUtil.SESSION_DURATION.toMinutes();

        assertEquals("", expectedSizeOfReturn, TimeSlotUtil.generateAllTimeSlotsWithinPeriod(from, to).size());
    }

    @Test
    public void testValidateTimeSlot() throws TimeSlotRequestException {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setSessionStartTime(LocalDateTime.of(2018,1,1, 9, 30));
        timeSlot.setDuration(TimeSlotUtil.SESSION_DURATION);

        TimeSlotUtil.validateTimeSlot(timeSlot);
    }

    @Test(expected = TimeSlotRequestException.class)
    public void testValidateTimeSlotThrowException() throws TimeSlotRequestException {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setSessionStartTime(LocalDateTime.of(2018,1,1, 8, 30));
        timeSlot.setDuration(TimeSlotUtil.SESSION_DURATION.plusHours(1));

        TimeSlotUtil.validateTimeSlot(timeSlot);
    }

}
