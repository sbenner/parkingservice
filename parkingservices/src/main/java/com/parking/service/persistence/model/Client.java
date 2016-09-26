package com.parking.service.persistence.model;

import com.parking.service.persistence.model.enums.UserRole;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "clients")
@Scope("session")
public class Client extends BaseEntity {

    private static final long serialVersionUID = -8533313806108532860L;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String carDetails;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private ClientGroup group;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean hasRequired() {
        return email != null

                && userName != null
                && getPassword() != null
                && group != null;
    }

    @Override
    public boolean isValidState() {
        return hasRequired();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return getGroup().getName().equals(UserRole.ADMIN);
    }

    public boolean isPremium() {
        return getGroup().getName().equals(UserRole.PREMIUM);
    }

    public boolean isRegular() {
        return getGroup().getName().equals(UserRole.REGULAR);
    }

    public BigDecimal getMonthlyFee(){
        return getGroup().getChargePerMonth();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientGroup getGroup() {
        return group;
    }

    public void setGroup(ClientGroup group) {
        this.group = group;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }
}
