package com.playpals.slotservice.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "current_pool_size")
    private Integer currentPoolSize;

    @Column(name = "pool_size")
    private Integer poolSize;

    @Column(name="name")
    private String name;


    @Column(name = "play_area_id")
    private Integer playAreaId;


    @Column(name = "sport_id")
    private Integer sport;


    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "court_id")
    private Integer courtId;

    @Column(name = "booking_status")
    private String bookingStatus;

    @Column(name = "event_status")
    private String eventStatus;

    @Column(name = "chatroomId")
    private UUID chatroomId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "event_name")
    private String eventName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrentPoolSize() {
        return currentPoolSize;
    }

    public void setCurrentPoolSize(Integer currentPoolSize) {
        this.currentPoolSize = currentPoolSize;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayAreaId() {
        return playAreaId;
    }

    public void setPlayAreaId(Integer playAreaId) {
        this.playAreaId = playAreaId;
    }

    public Integer getSport() {
        return sport;
    }

    public void setSport(Integer sport) {
        this.sport = sport;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public UUID getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(UUID chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
