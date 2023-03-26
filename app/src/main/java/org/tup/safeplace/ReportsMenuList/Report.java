package org.tup.safeplace.ReportsMenuList;

public class Report {

    String
            complainant_identity,
            report_details,
            report_status,
            date_reported,
            time_reported,
            year_reported,
            date_commited,
            time_commited,
            incident_type,

            barangay,
            police_substation,
            report_images_1,
            report_images_2,
            report_images_3,
            street

    ;

    public Report() {
    }

    public Report(String complainant_identity, String report_details, String report_status, String date_reported, String time_reported, String year_reported, String date_commited, String time_commited, String incident_type, String barangay, String police_substation, String report_images_1, String report_images_2, String report_images_3, String street) {
        this.complainant_identity = complainant_identity;
        this.report_details = report_details;
        this.report_status = report_status;
        this.date_reported = date_reported;
        this.time_reported = time_reported;
        this.year_reported = year_reported;
        this.date_commited = date_commited;
        this.time_commited = time_commited;
        this.incident_type = incident_type;
        this.barangay = barangay;
        this.police_substation = police_substation;
        this.report_images_1 = report_images_1;
        this.report_images_2 = report_images_2;
        this.report_images_3 = report_images_3;
        this.street = street;
    }

    public String getComplainant_identity() {
        return complainant_identity;
    }

    public void setComplainant_identity(String complainant_identity) {
        this.complainant_identity = complainant_identity;
    }

    public String getReport_details() {
        return report_details;
    }

    public void setReport_details(String report_details) {
        this.report_details = report_details;
    }

    public String getReport_status() {
        return report_status;
    }

    public void setReport_status(String report_status) {
        this.report_status = report_status;
    }

    public String getDate_reported() {
        return date_reported;
    }

    public void setDate_reported(String date_reported) {
        this.date_reported = date_reported;
    }

    public String getTime_reported() {
        return time_reported;
    }

    public void setTime_reported(String time_reported) {
        this.time_reported = time_reported;
    }

    public String getYear_reported() {
        return year_reported;
    }

    public void setYear_reported(String year_reported) {
        this.year_reported = year_reported;
    }

    public String getDate_commited() {
        return date_commited;
    }

    public void setDate_commited(String date_commited) {
        this.date_commited = date_commited;
    }

    public String getTime_commited() {
        return time_commited;
    }

    public void setTime_commited(String time_commited) {
        this.time_commited = time_commited;
    }

    public String getIncident_type() {
        return incident_type;
    }

    public void setIncident_type(String incident_type) {
        this.incident_type = incident_type;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getPolice_substation() {
        return police_substation;
    }

    public void setPolice_substation(String police_substation) {
        this.police_substation = police_substation;
    }

    public String getReport_images_1() {
        return report_images_1;
    }

    public void setReport_images_1(String report_images_1) {
        this.report_images_1 = report_images_1;
    }

    public String getReport_images_2() {
        return report_images_2;
    }

    public void setReport_images_2(String report_images_2) {
        this.report_images_2 = report_images_2;
    }

    public String getReport_images_3() {
        return report_images_3;
    }

    public void setReport_images_3(String report_images_3) {
        this.report_images_3 = report_images_3;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
