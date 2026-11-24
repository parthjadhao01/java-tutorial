package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// PaymentService and Order Service are in Circular Dependency
// solution to solve this ciruclar dependecy problem is use field and setter dependency injection

@Component
public class OrderService {
//    @Autowired
//    PaymentService paymentService; // works

    PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


//    public OrderService(PaymentService paymentService){
//        this.paymentService = paymentService;
//    }

    public void placeOrder(){
        System.out.println("Order placed");
        paymentService.pay();
    }

    public void orderDetail(){
        System.out.println("Product-1 : iphone etc");
    }


}
