package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        OrderService order = context.getBean(OrderService.class);
        order.placeOrder();

        OrderService order1 = context.getBean(OrderService.class);

        System.out.println(order == order1);
//        print True when scope of the beans are singletone
//        print False when scope of the beans are prototype

//        SingleTone
//        -> In singleTone scope IOC create only one single object whose reference are shared to everyone
//        -> SingleTone have Eager Beans means IoC Intialized the Object as program get started
//        -> suitable when class are stateless

//        Prototype
//        -> In Prototype scope IoC create create various object whenver beans are asked or we can say whenever dependency Injection happen
//        -> Protytpe have Lazy Beans mean IoC Intialized the Object when they are runned in program not at the start of the program
//        -> Sutiable when class have states


//        Other Scope apart from SingleTone and Prototype
//        1. Request
//        2. Session
//        3. Application
    }
}
