package com.playpals.slotservice.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import javax.persistence.*;

@Entity
@Table(name = "event_users")
public class EventUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "status")
    private String status;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Additional methods, like equals and hashCode, can be added as needed
}

