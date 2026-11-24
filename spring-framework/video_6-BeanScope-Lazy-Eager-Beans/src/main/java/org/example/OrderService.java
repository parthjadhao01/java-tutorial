package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
//@Lazy -> we can change the beans intilization in singletone from Eager -> Lazy
// but we cannot initlize eager Initlization to prototype
public class OrderService {

    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    PaymentService paymentService;

    public void placeOrder(){
        System.out.println("Order Placed succesfully");
        paymentService.Pay();
    }
}
