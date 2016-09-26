package com.parking.service.services;

import com.parking.service.common.utils.DateUtils;
import com.parking.service.persistence.model.Client;
import com.parking.service.persistence.model.Parking;
import com.parking.service.persistence.model.ParkingHouse;
import com.parking.service.persistence.repository.ClientRepository;
import com.parking.service.persistence.repository.ParkingHouseRepository;
import com.parking.service.persistence.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class ParkingService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingService.class);
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    ParkingHouseRepository parkingHouseRepository;



    public List<Parking> findParkingsForDate(long clientId, Calendar calendar) {
        List<Parking> p = null;
        try {

            String startOfMonth = DateUtils.min(calendar, true);
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            String endOfMonth = DateUtils.max(calendar, true);

            p = parkingRepository.findParkingsForDates(clientId, startOfMonth, endOfMonth);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return p;
    }

    public Parking findParkingById(Long id){
          return parkingRepository.findParkingById(id);
    }

    public List<Parking> findParkingsForDate(Calendar calendar) {
        List<Parking> p = null;
        try {

            String startOfMonth = DateUtils.min(calendar, true);
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            String endOfMonth = DateUtils.max(calendar, true);

            p = parkingRepository.findParkingsForDates(startOfMonth, endOfMonth);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return p;
    }

    public List<Parking> findParkingsForClient(long clientId) {
        List<Parking> p = null;
        try {


            p = parkingRepository.findParkingsByClientId(clientId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return p;
    }

    public Parking parkEntry(Long clientId) {

        List<Parking> parkingList = parkingRepository.findLatestEntryForClientId(clientId);
        if (parkingList != null && parkingList.size() > 0) return null;

        Parking parking = new Parking();
        Client client = clientRepository.findClientById(clientId);


        if (client != null) {
            parking.setClient(clientRepository.findClientById(clientId));
            parking.setCreatedDate(new Date());
            parking.setEntryDate(new Date());
        }
        return parkingRepository.save(parking);
    }

    public Parking parkDeparture(Long clientId) {

        List<Parking> parkingList = parkingRepository.findLatestEntryForClientId(clientId);

        Parking latestParking = parkingList.size() > 0 ? parkingList.get(0) : null;

        if (latestParking != null) {
            latestParking.setDepartureDate(new Date());
            return parkingRepository.save(latestParking);
        } else {
            return null;
        }


    }

    public ParkingHouse findParkingHouseById(Long id){
        return parkingHouseRepository.findParkingById(id);
    }

    public Parking createParkingFromDto(com.parking.service.common.dto.model.Parking parking) {
        Parking p = new Parking();
        p.setClient(clientRepository.findClientById(parking.getClientId()));
        p.setEntryDate(DateUtils.formatDatefromString(parking.getEntryDate()));
        p.setDepartureDate(DateUtils.formatDatefromString(parking.getDepartureDate()));
        p.setCreatedDate(new Date());
        p.setParkingHouse(parkingHouseRepository.findParkingById(parking.getParkingHouseId()));
        return p;
    }

    public int importParkingInfo(List<com.parking.service.common.dto.model.Parking> parkingList) {
        List<Parking> dbParkingList = new ArrayList<>();

        for (com.parking.service.common.dto.model.Parking parking : parkingList) {
            dbParkingList.add(createParkingFromDto(parking));
        }

        return parkingRepository.save(dbParkingList).size();
    }

    public void deleteAll(List<Parking> parkingList) {
        parkingRepository.delete(parkingList);
    }


}
