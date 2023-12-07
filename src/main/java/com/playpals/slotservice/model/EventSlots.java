package com.playpals.slotservice.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "event_slots")
public class EventSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Timestamp date;


    @Column(name = "slot_id")
    private Integer slot;


    @Column(name = "play_area_id")
    private Integer playAreaId;


    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "court_id")
    private Integer courtId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Integer getPlayAreaId() {
        return playAreaId;
    }

    public void setPlayAreaId(Integer playAreaId) {
        this.playAreaId = playAreaId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }
}
