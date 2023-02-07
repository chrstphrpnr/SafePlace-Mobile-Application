package org.tup.safeplace.PoliceStationMenuList;

import java.io.Serializable;

public class PoliceStation implements Serializable {
    String policestation_name,policestation_commander,policestation_location,policestation_schedule,policestation_contact,img;

    public PoliceStation() {
    }

    public PoliceStation(String policestation_name, String policestation_commander, String policestation_location, String policestation_schedule, String policestation_contact,String img) {
        this.policestation_name = policestation_name;
        this.policestation_commander = policestation_commander;
        this.policestation_location = policestation_location;
        this.policestation_schedule = policestation_schedule;
        this.policestation_contact = policestation_contact;
        this.img = img;

    }

    public String getPolicestation_name() {
        return policestation_name;
    }

    public void setPolicestation_name(String policestation_name) {
        this.policestation_name = policestation_name;
    }

    public String getPolicestation_commander() {
        return policestation_commander;
    }

    public void setPolicestation_commander(String policestation_commander) {
        this.policestation_commander = policestation_commander;
    }

    public String getPolicestation_location() {
        return policestation_location;
    }

    public void setPolicestation_location(String policestation_location) {
        this.policestation_location = policestation_location;
    }

    public String getPolicestation_schedule() {
        return policestation_schedule;
    }

    public void setPolicestation_schedule(String policestation_schedule) {
        this.policestation_schedule = policestation_schedule;
    }

    public String getPolicestation_contact() {
        return policestation_contact;
    }

    public void setPolicestation_contact(String policestation_contact) {
        this.policestation_contact = policestation_contact;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
