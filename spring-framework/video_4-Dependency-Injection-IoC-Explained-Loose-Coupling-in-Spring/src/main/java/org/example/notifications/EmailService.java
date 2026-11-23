package org.example.notifications;

public class EmailService implements NotificationServices {

    @Override
    public void sendNotifcation(){
        System.out.println("sending email notification");
    }

}
