package com.parking.service.persistence.repository;

import com.parking.service.persistence.model.Parking;
import com.parking.service.persistence.model.ParkingHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParkingHouseRepository extends JpaRepository<ParkingHouse, Long>, QueryDslPredicateExecutor<Parking> {

      ParkingHouse findParkingById(Long id);

}
