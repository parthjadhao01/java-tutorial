package org.example;

import org.example.Payment.CardPayment;
import org.example.Payment.PaymentService;
import org.example.Payment.UpiPayment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example")
// sometimes we cannot apply @Component annotation :
// 1. class that need some values in constructor apart from the dependency
// 2. class that are in external jar file
// to solve this problem we have @Bean Annotation here how we use it
public class AppConfig {
    @Bean
    public User createUser(){
        return new User("Parth",22); // 1. class that need some values in constructor apart from the dependenc
    }

//   we can do same for external jar file class 2. class that are in external jar file
//   instead of using @Component on class we can also intialze bean from here as we did for above
    @Bean
    @Qualifier("cp")
    public PaymentService createCardPayment(){
        return new CardPayment();
    }

    @Bean
    @Qualifier("upi")
    public PaymentService createUpiPayment(){
        return new UpiPayment();
    }

    @Bean
    public OrderService createOrderService(@Qualifier("upi") PaymentService paymentService){
        return new OrderService(paymentService);
    }

}
