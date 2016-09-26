package com.parking.service.common.dto.model;

public class Parking {


    private long clientId;
    private String entryDate;
    private String departureDate;
    private long parkingHouseId;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public long getParkingHouseId() {
        return parkingHouseId;
    }

    public void setParkingHouseId(long parkingHouseId) {
        this.parkingHouseId = parkingHouseId;
    }
}
