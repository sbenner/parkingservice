package com.parking.service.persistence.repository;

import com.parking.service.persistence.model.Parking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface  ParkingRepository extends JpaRepository<Parking, Long>, QueryDslPredicateExecutor<Parking> {

    Parking findParkingById(Long id);

    @Query(value = "select * from parkings where client_id=?1 and entry_date between ?2 and ?3 ",nativeQuery = true)
    List<Parking> findParkingsForDates(Long clientId,String entryDate,String departureDate);

    @Query(value = "select * from parkings where entry_date between ?1 and ?2 ",nativeQuery = true)
    List<Parking> findParkingsForDates(String entryDate,String departureDate);


    @Query(value = "select * from parkings where client_id=?1 and entry_date is not null and departure_date is null order by id desc limit 1 ",nativeQuery = true)
    List<Parking> findLatestEntryForClientId(Long clientId);

    List<Parking> findParkingsByClientId(Long clientId);


}
