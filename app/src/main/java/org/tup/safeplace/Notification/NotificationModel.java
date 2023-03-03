package org.tup.safeplace.Notification;

public class NotificationModel {

    private String message, status;

    public NotificationModel() {
    }

    public NotificationModel(String message,String status) {
        this.message = message;
        this.status = status;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
