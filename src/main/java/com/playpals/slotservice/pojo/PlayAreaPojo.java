package com.playpals.slotservice.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlayAreaPojo {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String owner;

    @Getter
    @Setter
    private String address1;

    @Getter
    @Setter
    private String address2;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private Integer zipcode;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String comments;

    @Getter
    @Setter
    private List<String> docUrls;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getDocUrls() {
        return docUrls;
    }

    public void setDocUrls(List<String> docUrls) {
        this.docUrls = docUrls;
    }

    // Constructor, getters, and setters
    public PlayAreaPojo() {
    }

    public PlayAreaPojo(Integer id, String name, String owner, String address1, String address2, String city,
                        String state, String country, Integer zipcode, String status, String comments,
                        List<String> docUrls) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.status = status;
        this.comments = comments;
        this.docUrls = docUrls;
    }
}
