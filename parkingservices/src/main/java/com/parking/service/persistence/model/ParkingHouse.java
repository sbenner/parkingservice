package com.parking.service.persistence.model;

import org.springframework.context.annotation.Scope;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parking_houses")
@Scope("session")
public class ParkingHouse extends BaseEntity {

    private static final long serialVersionUID = -8533313806108532860L;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
