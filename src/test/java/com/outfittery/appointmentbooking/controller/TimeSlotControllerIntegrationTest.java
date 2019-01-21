package com.outfittery.appointmentbooking.controller;

import com.outfittery.appointmentbooking.pojos.TimeSlot;
import com.outfittery.appointmentbooking.service.TimeSlotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(TimeSlotController.class)
public class TimeSlotControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TimeSlotService service;

    @Test
    public void givenFromAndToLocalDates_whenGetTimeSlots_theReturnEmptyListJson() throws Exception {

        Set<TimeSlot> mockResult = new HashSet<>();

        given(service.retrieveAllAvailableTimeSlots(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .willReturn(mockResult);

        mvc.perform(
                get("/api/timeslots/list")
                        .param("from","2020-01-01")
                        .param("to","2020-01-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableTimeSlots", hasSize(0)));
    }
}
