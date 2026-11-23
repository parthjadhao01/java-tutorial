package org.example;

import org.example.notifications.EmailService;
import org.example.notifications.NotificationServices;
import org.example.notifications.SMSService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
//      Dependency Inversion Principle as we are depending on the abstraction rather than the concrete implementation
//      IOC - Inversion of Control is a design principle in which the control of the flow of a program is inverted. In other words, instead of the program controlling the flow of execution, the control is inverted and given to a framework or container. This allows for more flexibility and easier testing, as the program can be easily modified without changing the underlying code.
        NotificationServices emailNotifcation = new EmailService();
//      Depenedency Inversion Principle as we are depending on the abstraction rather than the concrete implementation
        NotificationServices smsNotification = new SMSService();
//        Dependency Injection
        OrderService orderService = new OrderService(emailNotifcation);
        orderService.placeOrder();

//        as you can see above the DI is done through main in spring framework the DI is done through the spring container and the beans are injected into the classes automatically
    }
}
