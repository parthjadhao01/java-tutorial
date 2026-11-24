package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// PaymentService and Order Service are in Circular Dependency
// solution to solve this ciruclar dependecy problem is use field and setter dependency injection
@Component
public class PaymentService {
//    @Autowired
//    OrderService orderService; // works


    OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

//    public PaymentService(OrderService orderService){
//        this.orderService = orderService;
//    }

    public void pay(){
        System.out.println("Payment is done");
        orderService.orderDetail();
    }
}
