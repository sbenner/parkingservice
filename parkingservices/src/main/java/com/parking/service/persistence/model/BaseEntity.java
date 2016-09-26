package com.parking.service.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable, CheckSaveState {

    private static final long	serialVersionUID	= 4696391839059135100L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public BaseEntity() {
        // default
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int result = (id == null) ? 0 : id.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BaseEntity other = (BaseEntity) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName()).append("[");
        sb.append("id=").append(id);
        sb.append("]");
        return sb.toString();
    }

    @JsonIgnore
    @Override
    public boolean hasRequired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isValidState() {
        return true;
    }
}
