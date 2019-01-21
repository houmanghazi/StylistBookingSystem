package com.outfittery.appointmentbooking.controller;

import com.outfittery.appointmentbooking.dto.StylistRequest;
import com.outfittery.appointmentbooking.model.Stylist;
import com.outfittery.appointmentbooking.service.StylistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/stylists/")
@Api(value = "StylistsControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class StylistController {
    private static final Logger log = LoggerFactory.getLogger(StylistController.class);

    private StylistService stylistService;

    @Autowired
    public void setStylistService(StylistService stylistService) {
        this.stylistService = stylistService;
    }

    @PostMapping(path="/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Adds a new Stylist", notes = "Adds a new Stylist to the system")
    public Stylist createStylist(@RequestBody StylistRequest stylistRequest){
        Stylist stylist = new Stylist();
        stylist.setName(stylistRequest.getName());
        return stylistService.addStylist(stylist);
    }
}
