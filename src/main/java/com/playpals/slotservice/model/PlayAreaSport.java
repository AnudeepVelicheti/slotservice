package com.playpals.slotservice.model;

import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.persistence.Table;
        import javax.persistence.Column;

@Entity
@Table(name = "play_area_sports")
public class PlayAreaSport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "play_area_id")
    private Integer playAreaId;

    @Column(name = "sport_id")
    private Integer sportId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayAreaId() {
        return playAreaId;
    }

    public void setPlayAreaId(Integer playAreaId) {
        this.playAreaId = playAreaId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }


    // Getters and setters
}

