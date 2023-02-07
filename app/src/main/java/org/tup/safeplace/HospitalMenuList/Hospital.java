package org.tup.safeplace.HospitalMenuList;

import java.io.Serializable;

public class Hospital implements Serializable {
    String hospital_name,hospital_type,hospital_medical_director,hospital_location,hospital_schedule,hospital_contact,img;

    public Hospital() {
    }


    public Hospital(String hospital_name, String hospital_type, String hospital_medical_director, String hospital_location, String hospital_schedule, String hospital_contact, String img) {
        this.hospital_name = hospital_name;
        this.hospital_type = hospital_type;
        this.hospital_medical_director = hospital_medical_director;
        this.hospital_location = hospital_location;
        this.hospital_schedule = hospital_schedule;
        this.hospital_contact = hospital_contact;
        this.img = img;

    }



    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_type() {
        return hospital_type;
    }

    public void setHospital_type(String hospital_type) {
        this.hospital_type = hospital_type;
    }

    public String getHospital_medical_director() {
        return hospital_medical_director;
    }

    public void setHospital_medical_director(String hospital_medical_director) {
        this.hospital_medical_director = hospital_medical_director;
    }

    public String getHospital_location() {
        return hospital_location;
    }

    public void setHospital_location(String hospital_location) {
        this.hospital_location = hospital_location;
    }

    public String getHospital_schedule() {
        return hospital_schedule;
    }

    public void setHospital_schedule(String hospital_schedule) {
        this.hospital_schedule = hospital_schedule;
    }

    public String getHospital_contact() {
        return hospital_contact;
    }

    public void setHospital_contact(String hospital_contact) {
        this.hospital_contact = hospital_contact;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
