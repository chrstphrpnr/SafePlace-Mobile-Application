package org.tup.safeplace;

public class NotificationModel {

    private String message;

    public NotificationModel() {
    }

    public NotificationModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
