package com.parking.service.persistence.model;

import com.parking.service.persistence.converter.UserRoleConverter;
import com.parking.service.persistence.model.enums.UserRole;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "client_groups")
@Scope("session")
public class ClientGroup extends BaseEntity {

    private static final long serialVersionUID = -8533313806108532860L;
    @Convert(converter = UserRoleConverter.class)
    private UserRole name;

    @Column(nullable = true)
    private BigDecimal chargePerMonth;
    @Column(nullable = false)
    private BigDecimal dayTimeFee;
    @Column(nullable = false)
    private BigDecimal nightTimeFee;


    public UserRole getName() {
        return name;
    }

    public void setName(UserRole name) {
        this.name = name;
    }

    public BigDecimal getChargePerMonth() {
        return chargePerMonth;
    }

    public void setChargePerMonth(BigDecimal chargePerMonth) {
        this.chargePerMonth = chargePerMonth;
    }

    public BigDecimal getDayTimeFee() {
        return dayTimeFee;
    }

    public void setDayTimeFee(BigDecimal dayTimeFee) {
        this.dayTimeFee = dayTimeFee;
    }

    public BigDecimal getNightTimeFee() {
        return nightTimeFee;
    }

    public void setNightTimeFee(BigDecimal nightTimeFee) {
        this.nightTimeFee = nightTimeFee;
    }
}
