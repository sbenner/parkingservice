package com.parking.service.common.dto.model;

import com.parking.service.persistence.model.Client;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/27/16
 * Time: 4:52 AM
 */
public class Total {

    private long clientId;
    private int month;
    private BigDecimal total;

    private Client client;

    public Total(int month, BigDecimal total) {

        setMonth(month);
        setTotal(total);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
