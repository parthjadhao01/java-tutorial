package org.example;

import org.example.Payment.CardPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
//        In given below line we are running the IoC container
//        there are two way of using Spring Annotation
//        1. @Component Annotation
//        2. XML Configuration Based
//        In this given below example i am shwoing you Component Annotation Example
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

//        In spring object are bean speically which are dependent on each other
        OrderService orderService = context.getBean(OrderService.class);
        orderService.placeOrder();

//        User user = context.getBean(User.class);
    }

}
