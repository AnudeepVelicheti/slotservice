package com.playpals.slotservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "play_area_docs")
public class PlayAreaDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "play_area_id")
    private Integer playAreaId;

    @Column(name="type")
    private String type;

    @Column(name="name")
    private String name;

    @Column(name = "s3url")
    private String s3Url;


    public Integer getId() {
        return id;
    }

    public Integer getPlayAreaId() {
        return playAreaId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlayAreaId(Integer playAreaId) {
        this.playAreaId = playAreaId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getS3Url() {
        return s3Url;
    }







    // Getters and setters
}

