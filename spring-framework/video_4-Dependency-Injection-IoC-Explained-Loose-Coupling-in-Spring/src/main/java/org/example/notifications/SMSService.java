package org.example.notifications;

public class SMSService implements NotificationServices {
    @Override
    public void sendNotifcation() {
        System.out.println("Sending SMS Service");
    }
}
