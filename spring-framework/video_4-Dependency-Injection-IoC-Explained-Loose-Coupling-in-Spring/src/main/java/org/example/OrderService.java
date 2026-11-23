package org.example;

import org.example.notifications.EmailService;
import org.example.notifications.NotificationServices;

public class OrderService {

//    Tightly coupling we are creating a emailService dependency on Order Service
//    EmailService emailService = new EmailService();
//    LosseCoupling
//    NotificationServices notifcation = new EmailService();

//    Dependency Incjection
    NotificationServices notification;

    public OrderService(NotificationServices notification){
        this.notification = notification;
    }

    public void placeOrder(){
        System.out.println("Order Placed");
        notification.sendNotifcation();
    }
}
