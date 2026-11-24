package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
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
