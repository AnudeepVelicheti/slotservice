package com.playpals.slotservice.model;

import lombok.Getter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "play_areas")

public class PlayArea{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @Column(name = "owner")
    private Integer owner;
    @Getter
    @Column(name = "address1")
    private String address1;
    @Getter
    @Column(name = "address2")
    private String address2;
    @Getter
    @Column(name = "city")
    private String city;
    @Getter
    @Column(name = "state")
    private String state;
    @Getter
    @Column(name = "country")
    private String country;
    @Getter
    @Column(name = "zipcode")
    private Integer zipcode;

    @Getter
    @Column(name = "status")
    private String status;

    @Getter
    @Column(name = "comments")
    private String comments;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Getter
    @Column(name="request")
    private String request;


    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Integer getId() {
        return this.id;
    }



    public void setName(String name) {
        this.name=name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getOwner() {
        return owner;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public String getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }
}
