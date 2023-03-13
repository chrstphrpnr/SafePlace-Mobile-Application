package org.tup.safeplace.CallHistory;

import java.io.Serializable;

public class CallHistory implements Serializable {

    String name_contacted,type_contacted,date_contacted,time_contacted;

    public CallHistory() {
    }

    public CallHistory(String name_contacted, String type_contacted, String date_contacted, String time_contacted) {
        this.name_contacted = name_contacted;
        this.type_contacted = type_contacted;
        this.date_contacted = date_contacted;
        this.time_contacted = time_contacted;
    }

    public String getType_contacted() {
        return type_contacted;
    }

    public void setType_contacted(String type_contacted) {
        this.type_contacted = type_contacted;
    }

    public String getName_contacted() {
        return name_contacted;
    }

    public void setName_contacted(String name_contacted) {
        this.name_contacted = name_contacted;
    }

    public String getDate_contacted() {
        return date_contacted;
    }

    public void setDate_contacted(String date_contacted) {
        this.date_contacted = date_contacted;
    }

    public String getTime_contacted() {
        return time_contacted;
    }

    public void setTime_contacted(String time_contacted) {
        this.time_contacted = time_contacted;
    }
}
