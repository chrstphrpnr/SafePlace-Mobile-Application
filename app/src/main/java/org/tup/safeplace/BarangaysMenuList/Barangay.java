package org.tup.safeplace.BarangaysMenuList;

import java.io.Serializable;

public class Barangay implements Serializable {

    String barangay_name, barangay_captain, barangay_location, barangay_schedule, barangay_contact, img;

    public Barangay() {
    }

    public Barangay(String barangay_name, String barangay_captain, String barangay_location, String barangay_schedule, String barangay_contact, String img) {
        this.barangay_name = barangay_name;
        this.barangay_captain = barangay_captain;
        this.barangay_location = barangay_location;
        this.barangay_schedule = barangay_schedule;
        this.barangay_contact = barangay_contact;
        this.img = img;

    }

    public String getBarangay_name() {
        return barangay_name;
    }

    public void setBarangay_name(String barangay_name) {
        this.barangay_name = barangay_name;
    }

    public String getBarangay_captain() {
        return barangay_captain;
    }

    public void setBarangay_captain(String barangay_captain) {
        this.barangay_captain = barangay_captain;
    }

    public String getBarangay_location() {
        return barangay_location;
    }

    public void setBarangay_location(String barangay_location) {
        this.barangay_location = barangay_location;
    }

    public String getBarangay_schedule() {
        return barangay_schedule;
    }

    public void setBarangay_schedule(String barangay_schedule) {
        this.barangay_schedule = barangay_schedule;
    }

    public String getBarangay_contact() {
        return barangay_contact;
    }

    public void setBarangay_contact(String barangay_contact) {
        this.barangay_contact = barangay_contact;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
