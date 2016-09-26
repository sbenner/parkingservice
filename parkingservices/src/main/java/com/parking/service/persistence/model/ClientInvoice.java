package com.parking.service.persistence.model;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "client_invoices")
@Scope("session")
public class ClientInvoice extends BaseEntity {

    private static final long serialVersionUID = -8533313806108532860L;


    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name="client_id")
    private Client client;


    @ManyToOne(targetEntity = Parking.class)
    @JoinColumn(name="parking_id")
    private Parking parking;

    private BigDecimal total;

    private int parkingDate;

    private int parkingMonth;

    private Date createdDate;

   @Override
    public boolean isValidState() {
        return hasRequired();
    }



    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public int getParkingDate() {
        return parkingDate;
    }

    public void setParkingDate(int parkingDate) {
        this.parkingDate = parkingDate;
    }

    public int getParkingMonth() {
        return parkingMonth;
    }

    public void setParkingMonth(int parkingMonth) {
        this.parkingMonth = parkingMonth;
    }
}
