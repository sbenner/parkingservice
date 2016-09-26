package com.parking.service.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "parkings")
public class Parking extends BaseEntity {

    private static final long serialVersionUID = 2380396295782448905L;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @Column(nullable = false)
    private Date entryDate;
    @Column(nullable = false)
    private Date departureDate;
    @Column(nullable = false)
    private Date createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pkhs_id")
    private ParkingHouse parkingHouse;


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }




    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ParkingHouse getParkingHouse() {
        return parkingHouse;
    }

    public void setParkingHouse(ParkingHouse parkingHouse) {
        this.parkingHouse = parkingHouse;
    }
}
