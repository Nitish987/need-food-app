package com.needfood.client.models;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

public class Donate implements Serializable {
    private String address, by, city, id;
    private GeoPoint location;
    private String name, phone, photo;

    public Donate() {
    }

    public Donate(String address, String by, String city, String id, GeoPoint location, String name, String phone, String photo) {
        this.address = address;
        this.by = by;
        this.city = city;
        this.id = id;
        this.location = location;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
