package org.example;

import org.example.Payment.CardPayment;
import org.example.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class OrderService {
//    3. Filed dependency Injection (possible only in spring) NOT-RECOMMENDED
//    @Autowired
        PaymentService paymentService;

//      IoC container wires the dependency so we don't have to do wiring in the main
//      1. Constructor dependency Injection RECOMMENDED
        @Autowired
        public OrderService(@Qualifier("cp") PaymentService paymentService){
            this.paymentService = paymentService;
        }

    //    2. Setter Dependency Injection
    //    @Autowired
    //    public void setPaymentService(PaymentService paymentService){
    //        this.paymentService = paymentService;
    //    }

    //    Why Constructor Injection is Recommended
    //    1. Dependency get wired at the time of object creation
    //    2. final can be used
    //    3. easy to test the class


    public void placeOrder(){
        System.out.println("Place the order");
        paymentService.pay();

    }
}

// Internals of spring managing beans