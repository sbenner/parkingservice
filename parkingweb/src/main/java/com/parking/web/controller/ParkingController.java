package com.parking.web.controller;

import com.parking.service.persistence.model.Client;
import com.parking.service.persistence.model.Parking;
import com.parking.service.services.ParkingService;
import com.parking.web.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Handles requests for the parking.
 */
@Controller
public class ParkingController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);
    @Autowired
    ParkingService parkingService;

    @RequestMapping({"/listparkings"})
    public String listParkings(HttpServletRequest req, Model model, @RequestParam(name = "parkingId", required = false) String parkingId) {

        Client client = null;
        if (req.getSession() != null) {
            client = (Client) req.getSession().getAttribute("user");
        } else {
            return "login";
        }


        if (client != null && !client.isAdmin()) {
            List<Parking> parkingList = new ArrayList<>();

            if (parkingId != null) {
                parkingList.add(parkingService.findParkingById(Long.valueOf(parkingId)));
            } else {
                parkingList.addAll(parkingService.findParkingsForClient(client.getId()));
            }
            if (parkingList.size() > 0) {
                model.addAttribute("parkings", parkingList);
            } else {
                model.addAttribute("res", "sorry but you haven't parked in yet");
            }

            return "listparkings";
        } else if (client != null && client.isAdmin()) {
            Calendar calendar = Calendar.getInstance();
            List<Parking> parkingList = parkingService.findParkingsForDate(calendar);
            if (parkingList != null && parkingList.size() > 0) {
                model.addAttribute("parkings", parkingList);
            } else {
                model.addAttribute("res", "sorry but you haven't parked in yet");
            }
            return "listparkings";
        } else
            return "login";
    }

    @RequestMapping(value = "/entry/{clientId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse entry(@PathVariable String clientId) {
        Parking parking = null;

        try {
            parking = parkingService.parkEntry(Long.valueOf(clientId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return BaseResponse.response(parking);


    }

    @RequestMapping(value = "/importparkings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse importParkingList(@RequestBody List<com.parking.service.common.dto.model.Parking> parkingList) {
        try {
            if (parkingService.importParkingInfo(parkingList) > 0) {
                return BaseResponse.Created();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return BaseResponse.fail();
    }

    @RequestMapping(value = "/depart/{clientId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse depart(@PathVariable String clientId) {
        Parking parking = null;
        try {
            parking = parkingService.parkDeparture(Long.valueOf(clientId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return BaseResponse.response(parking);

    }
}
